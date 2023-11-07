package com.changeside.courseerpbackend.models.payload.otp;

import com.changeside.courseerpbackend.models.enums.otp.OTPChannel;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class BaseOTPChannelRequest {
    OTPChannel otpChannel;
}
