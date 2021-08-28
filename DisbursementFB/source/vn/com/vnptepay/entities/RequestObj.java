package vn.com.vnptepay.entities;

public class RequestObj {
	private String RequestId = "";
	private String RequestTime = "";
	private String PartnerCode = "";
	private String Operation = "";
	private String ReferenceId = "";
	private String BankNo = "";
	private String AccNo = "";
	private String AccType = "";
	private String RequestAmount = "";
	private String Memo = "";
	private String AccountName = "";
	private String Signature = "";
	private String extend;

	public String getPartnerCode() {
		return PartnerCode;
	}

	public void setPartnerCode(String partnerCode) {
		PartnerCode = partnerCode;
	}

	public String getMemo() {
		return Memo;
	}

	public void setMemo(String memo) {
		Memo = memo;
	}

	public String getRequestId() {
		return RequestId;
	}

	public void setRequestId(String requestId) {
		RequestId = requestId;
	}

	public String getRequestTime() {
		return RequestTime;
	}

	public void setRequestTime(String requestTime) {
		RequestTime = requestTime;
	}

	public String getOperation() {
		return Operation;
	}

	public void setOperation(String operation) {
		Operation = operation;
	}

	public String getReferenceId() {
		return ReferenceId;
	}

	public void setReferenceId(String referenceId) {
		ReferenceId = referenceId;
	}

	public String getBankNo() {
		return BankNo;
	}

	public void setBankNo(String bankNo) {
		BankNo = bankNo;
	}

	public String getAccNo() {
		return AccNo;
	}

	public void setAccNo(String accNo) {
		AccNo = accNo;
	}

	public String getAccType() {
		return AccType;
	}

	public void setAccType(String accType) {
		AccType = accType;
	}

	public String getRequestAmount() {
		return RequestAmount;
	}

	public void setRequestAmount(String requestAmount) {
		RequestAmount = requestAmount;
	}

	public String getSignature() {
		return Signature;
	}

	public void setSignature(String signature) {
		Signature = signature;
	}

	public String getAccountName() {
		return AccountName;
	}

	public void setAccountName(String accountName) {
		AccountName = accountName;
	}

	public String getExtend() {
		return extend;
	}

	public void setExtend(String extend) {
		this.extend = extend;
	}

}
