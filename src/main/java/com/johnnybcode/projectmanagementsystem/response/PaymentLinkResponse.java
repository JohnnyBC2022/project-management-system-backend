package com.johnnybcode.projectmanagementsystem.response;

import com.johnnybcode.projectmanagementsystem.model.PlanType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PaymentLinkResponse {

    private String payment_link_url;
    private String payment_link_id;


}
