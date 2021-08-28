package vn.com.vnptepay.test;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.json.JSONObject;

import com.google.gson.Gson;

import vn.com.vnptepay.security.RSA;
import vn.com.vnptepay.config.Config;
import vn.com.vnptepay.entities.Extend;
import vn.com.vnptepay.entities.RequestObj;

public class Test {

	public static void main(String[] args) {
		// BankNo Test
		String BankNo = "970423";
		// Bank account number test
		String AccNum = "13210013240000";
		// Bank card number test
		String CardNum = "970423002790000";
		// AccName tesst
		String AccName = "NGUYEN VAN A";
		String amount = "100000";
		String mess = "Test transfer";
		String phone = "0987654321";
		String email = "test@gmail.com";
		String customerId = "0192837465";
		String address = "Me tri, Nam Tu Liem, Ha Noi";

//		verifyAccount(AccNum, AccName, Config.ACCTYPE_ACCNUM, BankNo);
//		transferMoney(amount, AccNum, AccName, BankNo, Config.ACCTYPE_ACCNUM, mess, phone, email, customerId, address);
		queryTransStatus("PARTNERTEST0220210823105401");
//		checkBalance();
	}

	public static String verifyAccount(String accNo, String accName, String accType, String bankNo) {
		String RequestId = getRandomRequestId();
		String RequestTime = getDate("yyyy-MM-dd HH:mm:ss");
		String textToSign = RequestId + "|" + RequestTime + "|" + Config.partnerCode + "|" + Config.OPERATION_VERIFY
				+ "|" + bankNo + "|" + accNo + "|" + accType + "|" + accName;

		String Signature = "";
		try {
			Signature = RSA.signData(textToSign, Config.privatekey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestObj reqObj = new RequestObj();
		reqObj.setRequestId(RequestId);
		reqObj.setRequestTime(RequestTime);
		reqObj.setPartnerCode(Config.partnerCode);
		reqObj.setOperation(Config.OPERATION_VERIFY);
		reqObj.setBankNo(bankNo);
		reqObj.setAccNo(accNo);
		reqObj.setAccType(accType);
		reqObj.setAccountName(accName);
		reqObj.setSignature(Signature);

		Gson json = new Gson();
		String dataReq = json.toJson(reqObj);

		System.out.println("REQUEST DATA: \n" + dataReq);

		String result = ClientPostRequest.postRequest(Config.url, dataReq);
		System.out.println("\nURL: " + Config.url);
		System.out.println("\n========================\nRESPONSE DATA:\n");
		System.out.println(result + "\n");

		JSONObject obj = new JSONObject(result);

		System.out.println("Account verification results: " + obj.getString("ResponseInfo") + "\n");

		String data = obj.getInt("ResponseCode") + "|" + obj.getString("ResponseMessage") + "|"
				+ obj.getString("RequestId") + "|" + obj.getString("BankNo") + "|" + obj.getString("AccNo") + "|"
				+ obj.getString("AccType") + "|" + obj.getString("ResponseInfo");
		boolean verifySign = RSA.verify(data, obj.getString("Signature"), Config.publickey);
		System.out.println("\n========================\nVERIFY RESPONSE SIGNATURE:\n");
		System.out.println("Verify Response Signature: " + verifySign);

		return result;
	}

	public static String transferMoney(String reqAmount, String accNo, String accName, String bankNo, String accType,
			String contentMess, String phone, String email, String customerId, String addr) {
		if (contentMess.length() > 100) {
			System.out.println("Noi dung chuyen tien khong duoc dai qua 100 ky tu.");
			return null;
		}
		String RequestId = getRandomRequestId();
		String RequestTime = getDate("yyyy-MM-dd HH:mm:ss");
		String referenceId = getRandomReferenceId(Config.partnerCode);

		String textToSign = RequestId + "|" + RequestTime + "|" + Config.partnerCode + "|" + Config.OPERATION_TRANSFER
				+ "|" + referenceId + "|" + bankNo + "|" + accNo + "|" + accType + "|" + reqAmount + "|" + contentMess;
		String Signature = "";
		try {
			Signature = RSA.signData(textToSign, Config.privatekey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestObj reqObj = new RequestObj();
		Extend extend = new Extend(phone, email, addr, customerId);
		Gson json = new Gson();

		reqObj.setRequestId(RequestId);
		reqObj.setRequestTime(RequestTime);
		reqObj.setPartnerCode(Config.partnerCode);
		reqObj.setOperation(Config.OPERATION_TRANSFER);
		reqObj.setBankNo(bankNo);
		reqObj.setAccNo(accNo);
		reqObj.setAccType(accType);
		reqObj.setReferenceId(referenceId);
		reqObj.setRequestAmount(reqAmount);
		reqObj.setMemo(contentMess);
		reqObj.setSignature(Signature);
		reqObj.setAccountName(accName);
		reqObj.setExtend(json.toJson(extend));

		String dataReq = json.toJson(reqObj);

		System.out.println("REQUEST DATA: \n" + dataReq);

		String result = ClientPostRequest.postRequest(Config.url, dataReq);
		System.out.println("\n========================\nRESPONSE DATA:\n");
		System.out.println(result);
		System.out.println("\n========================\nVERIFY RESPONSE SIGNATURE:\n");

		JSONObject obj = new JSONObject(result);
		String data = obj.getInt("ResponseCode") + "|" + obj.getString("ResponseMessage") + "|"
				+ obj.getString("ReferenceId") + "|" + obj.getString("TransactionId") + "|"
				+ obj.getString("TransactionTime") + "|" + obj.getString("BankNo") + "|" + obj.getString("AccNo") + "|"
				+ obj.getString("AccName") + "|" + obj.getInt("AccType") + "|" + obj.getInt("RequestAmount") + "|"
				+ obj.getInt("TransferAmount");
		boolean verifySign = RSA.verify(data, obj.getString("Signature"), Config.publickey);
		System.out.println("Verify Response Signature: " + verifySign);

		return result;
	}

	public static String queryTransStatus(String ReferenceId) {
		String RequestId = getRandomRequestId();
		String RequestTime = getDate("yyyy-MM-dd HH:mm:ss");

		String textToSign = RequestId + "|" + RequestTime + "|" + Config.partnerCode + "|"
				+ Config.OPERATION_QUERY_TRANS + "|" + ReferenceId;
		String Signature = "";
		try {
			Signature = RSA.signData(textToSign, Config.privatekey);
		} catch (Exception e) {
			e.printStackTrace();
		}

		RequestObj reqObj = new RequestObj();
		reqObj.setRequestId(RequestId);
		reqObj.setRequestTime(RequestTime);
		reqObj.setPartnerCode(Config.partnerCode);
		reqObj.setOperation(Config.OPERATION_QUERY_TRANS);
		reqObj.setReferenceId(ReferenceId);
		reqObj.setSignature(Signature);

		Gson json = new Gson();
		String dataReq = json.toJson(reqObj);

		System.out.println("REQUEST DATA: \n" + dataReq);

		String result = ClientPostRequest.postRequest(Config.url, dataReq);
		System.out.println("\n========================\nRESPONSE DATA:\n");
		System.out.println(result);
		System.out.println("\n========================\nVERIFY RESPONSE SIGNATURE:\n");

		JSONObject obj = new JSONObject(result);
		JSONObject transInfoObj = obj.getJSONObject("InfoTransaction");
		String data = transInfoObj.get("Status").toString() + "|"
					+ transInfoObj.get("StatusDes").toString() + "|"
					+ transInfoObj.get("ReferenceId").toString() + "|"
					+ transInfoObj.get("TransactionId").toString() + "|"
					+ transInfoObj.getString("TransactionTime") + "|"
					+ transInfoObj.get("BankNo").toString() + "|"
					+ transInfoObj.get("AccNo").toString() + "|"
					+ transInfoObj.get("AccName").toString() + "|"
					+ transInfoObj.get("AccType").toString() + "|"
					+ transInfoObj.get("RequestAmount").toString() + "|"
					+ transInfoObj.get("TransferAmount").toString();
		
		System.out.println("Data Sign: " + data);
		
		boolean verifySign = RSA.verify(data, obj.getString("Signature"), Config.publickey);
		System.out.println("Verify Response Signature: " + verifySign);

		return result;
	}

	public static String checkBalance() {
		String RequestId = getRandomRequestId();
		String RequestTime = getDate("yyyy-MM-dd HH:mm:ss");

		String textToSign = RequestId + "|" + RequestTime + "|" + Config.partnerCode + "|"
				+ Config.OPERATION_CHECK_BALANCE;

		String Signature = "";
		try {
			Signature = RSA.signData(textToSign, Config.privatekey);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestObj reqObj = new RequestObj();
		reqObj.setRequestId(RequestId);
		reqObj.setRequestTime(RequestTime);
		reqObj.setPartnerCode(Config.partnerCode);
		reqObj.setOperation(Config.OPERATION_CHECK_BALANCE);
		reqObj.setSignature(Signature);

		Gson json = new Gson();
		String dataReq = json.toJson(reqObj);

		System.out.println("REQUEST DATA: \n" + dataReq);

		String result = ClientPostRequest.postRequest(Config.url, dataReq);
		System.out.println("\n========================\nRESPONSE DATA:\n");
		System.out.println(result);
		System.out.println("\n========================\nVERIFY RESPONSE SIGNATURE:\n");

		JSONObject obj = new JSONObject(result);
		String data = obj.getInt("ResponseCode") + "|" + obj.getString("ResponseMessage") + "|"
				+ obj.getString("PartnerCode") + "|" + obj.getLong("CurrentBalance");
		System.out.println("Clear text: " + data);
		boolean verifySign = RSA.verify(data, obj.getString("Signature"), Config.publickey);
		System.out.println("Verify Response Signature: " + verifySign);

		return result;
	}

	public static String getRandomRequestId() {
		// int i = new Random().nextInt(900) + 100;
		String requestId = "RQID" + getDate("yyyyMMddHHmmss") + "_" + (new Random().nextInt(900) + 100)
				+ (new Random().nextInt(900) + 100);
		return requestId;
	}

	public static String getRandomReferenceId(String merchantCode) {
		String referenceId = merchantCode + getDate("yyyyMMddHHmmss");
		return referenceId;
	}

	public static String getDate(String fmOfTime) {
		Date date = new Date();
		SimpleDateFormat ft = new SimpleDateFormat(fmOfTime);
		return ft.format(date);
	}
}
