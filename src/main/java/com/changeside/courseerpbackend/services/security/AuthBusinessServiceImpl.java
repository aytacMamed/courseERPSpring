package com.changeside.courseerpbackend.services.security;

import com.changeside.courseerpbackend.exception.BaseException;
import com.changeside.courseerpbackend.models.dto.RefreshTokenDto;
import com.changeside.courseerpbackend.models.enums.branch.BranchStatus;
import com.changeside.courseerpbackend.models.mappers.CourseEntityMapper;
import com.changeside.courseerpbackend.models.mappers.UserEntityMapper;
import com.changeside.courseerpbackend.models.mybatis.branch.Branch;
import com.changeside.courseerpbackend.models.mybatis.course.Course;
import com.changeside.courseerpbackend.models.mybatis.employee.Employee;
import com.changeside.courseerpbackend.models.mybatis.role.Role;
import com.changeside.courseerpbackend.models.mybatis.user.User;
import com.changeside.courseerpbackend.models.payload.auth.LoginPayload;
import com.changeside.courseerpbackend.models.payload.auth.RefreshTokenPayload;
import com.changeside.courseerpbackend.models.payload.auth.SignUpPayload;
import com.changeside.courseerpbackend.models.payload.otp.BaseOTPChannelRequest;
import com.changeside.courseerpbackend.models.payload.otp.BaseOTPRequest;
import com.changeside.courseerpbackend.models.response.auth.LoginResponse;
import com.changeside.courseerpbackend.services.branch.BranchService;
import com.changeside.courseerpbackend.services.course.CourseService;
import com.changeside.courseerpbackend.services.employee.EmployeeService;
import com.changeside.courseerpbackend.services.otp.OTPFactory;
import com.changeside.courseerpbackend.services.role.RoleService;
import com.changeside.courseerpbackend.services.user.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import static com.changeside.courseerpbackend.models.enums.response.ErrorResponseMessages.*;

@Service
@RequiredArgsConstructor
@Slf4j
public class AuthBusinessServiceImpl implements AuthBusinessService {

    private final static String BRANCH_NAME_DEFAULT_PATTERN = "%s Default Branch";
    private final AuthenticationManager authenticationManager;
    private final AccessTokenManager accessTokenManager;
    private final RefreshTokenManager refreshTokenManager;
    private final UserService userService;
    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;
    private final RoleService roleService;
    private final CourseService courseService;
    private final BranchService branchService;
    private final EmployeeService employeeService;

    @Override
    public LoginResponse login(LoginPayload loginPayload) {
        authenticate(loginPayload);
        return prepareLoginResponse(loginPayload.getEmail(), loginPayload.isRememberMe());

    }

    @Override
    public LoginResponse refresh(RefreshTokenPayload refreshTokenPayload) {
        return prepareLoginResponse(refreshTokenManager.
                getEmail(refreshTokenPayload.getRefreshToken()), refreshTokenPayload.isRememberMe());

    }

    @Override
    public void logout() {
        UserDetails userDetails = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        log.info(" {} user logout succeed ", userDetails.getUsername());
    }

    @Override
    public void singUp(SignUpPayload payload) {
        if (userService.checkByEmail(payload.getEmail())) {
            throw BaseException.of(EMAIL_ALREADY_REGISTERED);
        }
        Role defaultRole = roleService.getDefaultRole();
        //Stage 1:User insert
        User user =
                UserEntityMapper.INSTANCE.fromSignUpPayloadToUser
                        (payload, passwordEncoder.encode(payload.getPassword()), defaultRole.getId());
        userService.insert(user);
        //Stage 2:Course insert
        Course course = CourseEntityMapper.INSTANCE.fromSingUpPayload(payload);
        courseService.insert(course);

        //Stage 3:Default branch insert
        branchService.insert( populateDefaultBranchData(payload,course));

        //Stage 4:Employee insert
        employeeService.insert(Employee.builder().userId(user.getId()).build());

    }

    @Override
    public void singUpOTP(BaseOTPChannelRequest payload) {
        //TODO:OTP Processing....
        OTPFactory.handle(payload.getOtpChannel()).send();
    }

    @Override
    public void singUpOTPConfirmation(BaseOTPRequest payload) {
        log.info(payload.getOtp()+ " confirmed!");
    }

    @Override
    public void setAuthentication(String email) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(email);

        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(userDetails, " ", userDetails.getAuthorities())
        );
    }



    //private util methods

    private void authenticate(LoginPayload request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
            );
        } catch (AuthenticationException e) {
            throw e.getCause() instanceof BaseException ? (BaseException) e.getCause() : BaseException.unexpected();
        }
    }

    private LoginResponse prepareLoginResponse(String email, boolean rememberMe) {
        User user = userService.getByEmail(email);
        return LoginResponse.builder().
                accessToken(accessTokenManager.generate(user)).
                refreshToken(refreshTokenManager.generate(RefreshTokenDto.
                        builder().
                        user(user).
                        rememberMe(rememberMe).
                        build())).
                build();
    }

    private Branch populateDefaultBranchData(SignUpPayload payload,Course course) {
        //TODO:Customize field setters
        return Branch.builder().
                name(BRANCH_NAME_DEFAULT_PATTERN.formatted(payload.getCourseName())).
                status(BranchStatus.ACTIVE).
                address(payload.getAddress()).
                courseId(course.getId()).
                build();
    }
}
