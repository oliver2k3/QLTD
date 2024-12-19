package com.nhom7.qltd.entity;//package com.nhom7.qltd.API.entity;
//
//import jakarta.persistence.*;
//import lombok.Data;
//
//import java.time.LocalDateTime;
//
//@Entity
//@Table(name = "LoanContract_Detail")
//@Data
//public class LoanContract_Detail {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    @Column(name = "ID")
//    private int id;
//    @OneToOne
//    @JoinColumn(name = "ID_LoanContract", referencedColumnName = "ID")
//    private LoanContract loanContract;
//    @Column(name = "ID_Loan")
//    private int loanId;
//    @Column(name = "amount")
//    private double amount;
//    @Column(name = "interest_rate")
//    private double interestRate;
//    @Column(name = "duration")
//    private int duration;
//    @Column(name = "status")
//    private String status;
//    @Column(name = "created_date")
//    private LocalDateTime createdDate;
//    @Column(name = "updated_date")
//    private LocalDateTime updatedDate;
//    @Column(name = "end_date")
//    private LocalDateTime endDate;
//    @Column(name = "EMI")
//    private float emi;
//    @Column(name = "TotalPayment")
//    private float totalPayment;
//    @Column(name = "TotalInterest")
//    private float totalInterest;
//    @Column(name = "DaTra")
//    private float paid;
//    @Column(name = "ConLai")
//    private float remaining;
//    @Column(name = "LastPayment")
//    private LocalDateTime lastPayment;
//    @Column(name = "NextPayment")
//    private LocalDateTime nextPayment;
//    @Column(name = "Amount_This_Month")
//    private float amountThisMonth;
//}
