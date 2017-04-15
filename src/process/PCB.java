package process;
//package com.box.process.tools;

import com.alibaba.fastjson.JSONObject;

public class PCB {
	private int runTime;  //����ʱ��
    private  int priority;   //���ȼ�
    private  String name; //��������
    private boolean isOver; //�Ƿ����н���
    private int startTime;  //��ʼ����ʱ��
    private int status;//����״̬

    /* status��Ӧ��������
     * 0������
     * 1������
     * 2������
     * -1������
     * */
    public int getRunTime() {
		return runTime;
	}

	public void setRunTime(int runTime) {
		this.runTime = runTime;
	}


	public int getPriority() {
		return priority;
	}

	public int getStatus(){
		return status;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}



	public String getName() {
		return name;
	}



	public void setName(String name) {
		this.name = name;
	}
	
	public void setStatus(int status){
		this.status = status;
	}



	public boolean getIsOver() {
		return isOver;
	}


	public void setIsOver(boolean isOver) {
		this.isOver = isOver;
	}


	public int getStartTime() {
		return startTime;
	}

	public void setStartTime(int startTime) {
		this.startTime = startTime;
	}

	public void Json2Object(JSONObject jsonObject) {
		setName(jsonObject.getString("name"));
		setStartTime(jsonObject.getInteger("startTime"));
		setIsOver(jsonObject.getBooleanValue("isOver"));
		setPriority(jsonObject.getIntValue("priority"));
		setRunTime(jsonObject.getIntValue("runTime"));
	}


	public void decrease() {
		 runTime--;
		 if(runTime<=0){
			 isOver=true;
		 }
	}


}
