package com.example.fit5046_lab5_groupe;

import java.io.Serializable;
import java.util.List;

public class ReportBean implements Serializable {

    private int length;
    private int maxPageLimit;
    private int totalRecords;
    private RequestPayloadBean requestPayload;
    private PaginationBean pagination;
    private List<DataBean> data;

    public int getLength() {
        return length;
    }

    public void setLength(int length) {
        this.length = length;
    }

    public int getMaxPageLimit() {
        return maxPageLimit;
    }

    public void setMaxPageLimit(int maxPageLimit) {
        this.maxPageLimit = maxPageLimit;
    }

    public int getTotalRecords() {
        return totalRecords;
    }

    public void setTotalRecords(int totalRecords) {
        this.totalRecords = totalRecords;
    }

    public RequestPayloadBean getRequestPayload() {
        return requestPayload;
    }

    public void setRequestPayload(RequestPayloadBean requestPayload) {
        this.requestPayload = requestPayload;
    }

    public PaginationBean getPagination() {
        return pagination;
    }

    public void setPagination(PaginationBean pagination) {
        this.pagination = pagination;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class RequestPayloadBean implements Serializable {
        /**
         * structure : {"date":"date","areaName":"areaName","areaCode":"areaCode","confirmedRate":"cumCasesByPublishDateRate","confirmedNew":"newCasesByPublishDate","confirmed":"cumCasesByPublishDate","deathNew":"newDeaths28DaysByPublishDate","death":"cumDeaths28DaysByPublishDate","deathRate":"cumDeaths28DaysByPublishDateRate","latestBy":"newCasesByPublishDate"}
         * filters : [{"identifier":"areaType","operator":"=","value":"overview"}]
         * page : 1
         */

        private StructureBean structure;
        private int page;
        private List<FiltersBean> filters;

        public static class StructureBean implements Serializable {
            /**
             * date : date
             * areaName : areaName
             * areaCode : areaCode
             * confirmedRate : cumCasesByPublishDateRate
             * confirmedNew : newCasesByPublishDate
             * confirmed : cumCasesByPublishDate
             * deathNew : newDeaths28DaysByPublishDate
             * death : cumDeaths28DaysByPublishDate
             * deathRate : cumDeaths28DaysByPublishDateRate
             * latestBy : newCasesByPublishDate
             */

            private String date;
            private String areaName;
            private String areaCode;
            private String confirmedRate;
            private String confirmedNew;
            private String confirmed;
            private String deathNew;
            private String death;
            private String deathRate;
            private String latestBy;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public String getAreaName() {
                return areaName;
            }

            public void setAreaName(String areaName) {
                this.areaName = areaName;
            }

            public String getAreaCode() {
                return areaCode;
            }

            public void setAreaCode(String areaCode) {
                this.areaCode = areaCode;
            }

            public String getConfirmedRate() {
                return confirmedRate;
            }

            public void setConfirmedRate(String confirmedRate) {
                this.confirmedRate = confirmedRate;
            }

            public String getConfirmedNew() {
                return confirmedNew;
            }

            public void setConfirmedNew(String confirmedNew) {
                this.confirmedNew = confirmedNew;
            }

            public String getConfirmed() {
                return confirmed;
            }

            public void setConfirmed(String confirmed) {
                this.confirmed = confirmed;
            }

            public String getDeathNew() {
                return deathNew;
            }

            public void setDeathNew(String deathNew) {
                this.deathNew = deathNew;
            }

            public String getDeath() {
                return death;
            }

            public void setDeath(String death) {
                this.death = death;
            }

            public String getDeathRate() {
                return deathRate;
            }

            public void setDeathRate(String deathRate) {
                this.deathRate = deathRate;
            }

            public String getLatestBy() {
                return latestBy;
            }

            public void setLatestBy(String latestBy) {
                this.latestBy = latestBy;
            }
        }

        public static class FiltersBean implements Serializable {
            /**
             * identifier : areaType
             * operator : =
             * value : overview
             */

            private String identifier;
            private String operator;
            private String value;

            public String getIdentifier() {
                return identifier;
            }

            public void setIdentifier(String identifier) {
                this.identifier = identifier;
            }

            public String getOperator() {
                return operator;
            }

            public void setOperator(String operator) {
                this.operator = operator;
            }

            public String getValue() {
                return value;
            }

            public void setValue(String value) {
                this.value = value;
            }
        }
    }

    public static class PaginationBean implements Serializable {
        /**
         * current : /v1/data?filters=areaType=overview&structure={"date":"date","areaName":"areaName","areaCode":"areaCode","confirmedRate":"cumCasesByPublishDateRate","confirmedNew":"newCasesByPublishDate","confirmed":"cumCasesByPublishDate","deathNew":"newDeaths28DaysByPublishDate","death":"cumDeaths28DaysByPublishDate","deathRate":"cumDeaths28DaysByPublishDateRate","latestBy": "newCasesByPublishDate"}&format=json&page=1
         * next : null
         * previous : null
         * first : /v1/data?filters=areaType=overview&structure={"date":"date","areaName":"areaName","areaCode":"areaCode","confirmedRate":"cumCasesByPublishDateRate","confirmedNew":"newCasesByPublishDate","confirmed":"cumCasesByPublishDate","deathNew":"newDeaths28DaysByPublishDate","death":"cumDeaths28DaysByPublishDate","deathRate":"cumDeaths28DaysByPublishDateRate","latestBy": "newCasesByPublishDate"}&format=json&page=1
         * last : /v1/data?filters=areaType=overview&structure={"date":"date","areaName":"areaName","areaCode":"areaCode","confirmedRate":"cumCasesByPublishDateRate","confirmedNew":"newCasesByPublishDate","confirmed":"cumCasesByPublishDate","deathNew":"newDeaths28DaysByPublishDate","death":"cumDeaths28DaysByPublishDate","deathRate":"cumDeaths28DaysByPublishDateRate","latestBy": "newCasesByPublishDate"}&format=json&page=1
         */

        private String current;
        private Object next;
        private Object previous;
        private String first;
        private String last;

        public String getCurrent() {
            return current;
        }

        public void setCurrent(String current) {
            this.current = current;
        }

        public Object getNext() {
            return next;
        }

        public void setNext(Object next) {
            this.next = next;
        }

        public Object getPrevious() {
            return previous;
        }

        public void setPrevious(Object previous) {
            this.previous = previous;
        }

        public String getFirst() {
            return first;
        }

        public void setFirst(String first) {
            this.first = first;
        }

        public String getLast() {
            return last;
        }

        public void setLast(String last) {
            this.last = last;
        }
    }

    public static class DataBean implements Serializable {
        /**
         * date : 2022-05-13
         * areaName : United Kingdom
         * areaCode : K02000001
         * confirmedRate : 33034.3
         * latestBy : null
         * confirmed : 22159805
         * deathNew : null
         * death : null
         * deathRate : null
         */

        private String date;
        private String areaName;
        private String areaCode;
        private double confirmedRate;
        private Object latestBy;
        private int confirmed;
        private Object deathNew;
        private Object death;
        private Object deathRate;

        public String getDate() {
            return date;
        }

        public void setDate(String date) {
            this.date = date;
        }

        public String getAreaName() {
            return areaName;
        }

        public void setAreaName(String areaName) {
            this.areaName = areaName;
        }

        public String getAreaCode() {
            return areaCode;
        }

        public void setAreaCode(String areaCode) {
            this.areaCode = areaCode;
        }

        public double getConfirmedRate() {
            return confirmedRate;
        }

        public void setConfirmedRate(double confirmedRate) {
            this.confirmedRate = confirmedRate;
        }

        public Object getLatestBy() {
            return latestBy;
        }

        public void setLatestBy(Object latestBy) {
            this.latestBy = latestBy;
        }

        public int getConfirmed() {
            return confirmed;
        }

        public void setConfirmed(int confirmed) {
            this.confirmed = confirmed;
        }

        public Object getDeathNew() {
            return deathNew;
        }

        public void setDeathNew(Object deathNew) {
            this.deathNew = deathNew;
        }

        public Object getDeath() {
            return death;
        }

        public void setDeath(Object death) {
            this.death = death;
        }

        public Object getDeathRate() {
            return deathRate;
        }

        public void setDeathRate(Object deathRate) {
            this.deathRate = deathRate;
        }
    }
}
