package entities;

import java.util.Date;

import entities_enums.OperationType;

public class Operation {
   private long id;
   private OperationType opType;
   private Date execDate;
   
   private Account sourceAccount;
   private Account destAccount;
   
   public Account getSourceAccount() {
	return sourceAccount;
}

public void setSourceAccount(Account sourceAccount) {
	this.sourceAccount = sourceAccount;
}

public Account getDestAccount() {
	return destAccount;
}

public void setDestAccount(Account destAccount) {
	this.destAccount = destAccount;
}

public Operation() {}

public long getId() {
	return id;
}

public void setId(long id) {
	this.id = id;
}

public OperationType getOpType() {
	return opType;
}

public void setOpType(OperationType opType) {
	this.opType = opType;
}

public Date getExecDate() {
	return execDate;
}

public void setExecDate(Date execDate) {
	this.execDate = execDate;
}
}
