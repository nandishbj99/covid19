package com.app.covid19trackerindia;

public class PatientData {


        private String reportedOn;
    private String id;
        private String city;
        private String district;
        private String state;
     private String status;
     private String notes;

    public PatientData(){
        this.id="";
        this.reportedOn="";
        this.district="";
        this.city="";
        this.status="";
        this.state="";
        this.notes="";
    }

        public String getId(){
            return id;
        }
        public String getReportedOn(){
            return reportedOn;
        }
        public String getCity(){
            return city;
        }
        public String getDistrict(){
            return district;
        }
        public String getState(){
            return state;
        }
        public String getStatus(){
            return status;
        }
    public String getNotes(){
        return notes;
    }
        public void setReportedOn(String reportedOn){
            this.reportedOn=reportedOn;
        }
        public void setCity(String city){
            this.city=city;
        }
        public void setDistrict(String district){
            this.district=district;
        }
        public void setState(String state){
            this.state=state;
        }
        public void setStatus(String status){
            this.status=status;
        }
        public void setId(String id){
            this.id=id;
        }
    public void setNotes(String notes){
        this.notes=notes;
    }
    public void set(String all,String value){
        switch(all){
            case "patientId":
                this.id=value;
                break;
            case "reportedOn":
                this.reportedOn=value;
                break;
            case "city":
                this.city=value;
                break;
            case "district":
                this.district=value;
                break;
            case "state":
                this.state=value;
                break;
            case "status":
                this.status=value;
                break;
            case "notes":
                this.notes=value;
                break;

        }
    }



    }
