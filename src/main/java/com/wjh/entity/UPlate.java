package com.wjh.entity;

public class UPlate {
    public static final String EXIST = "exist";//有U盘
    public static final String NOT_EXIST = "not_exist";//无U盘

    private String  status;//有无U盘 取值：EXIST NOT_EXIST
    private String name;//在当前电脑中该U盘的名称 例如 E
    private Double capacity;//U盘的容量 单位 GB
    private Double measureTime;//测量该U盘实际花的时间，单位 分钟
    private Double estimateTime;//预计测量该U盘需要的时间，单位 分钟

    public UPlate() {
       this.status = NOT_EXIST;
    }

    public String  getStatus() {
        return status;
    }

    public String getName() {
        return name;
    }

    public Double getCapacity() {
        return capacity;
    }

    public Double getMeasureTime() {
        return measureTime;
    }

    public Double getEstimateTime() {
        return estimateTime;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCapacity(Double capacity) {
        this.capacity = capacity;
    }

    public void setMeasureTime(Double measureTime) {
        this.measureTime = measureTime;
    }

    public void setEstimateTime(Double estimateTime) {
        this.estimateTime = estimateTime;
    }

    @Override
    public String toString() {
        return "UPlate{" +
                "status=" + status +
                ", name='" + name + '\'' +
                ", capacity=" + capacity +
                ", measureTime=" + measureTime +
                ", estimateTime=" + estimateTime +
                '}';
    }
}
