package com.pro.salon.cattocdi.models;

import com.google.android.gms.maps.model.LatLng;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Salon implements Serializable{
    private int salonId;
    @SerializedName("SalonName")
    private String name;
    @SerializedName("Address")
    private String address;
    private Boolean isForMen;
    private Boolean isForWomen;
    @SerializedName("Username")
    private String username;
    @SerializedName("Password")
    private String password;
    @SerializedName("Email")
    private String email;
    @SerializedName("Phone")
    private String phone;
    private String LoggedOn;
    @SerializedName("Role")
    private String role;
    private float ratingNumber;
    private boolean full;
    private int discount;

    @SerializedName("ImageUrl")
    private String imageUrl;

    private int reviewsAmount;
    private Timestamp startTime;

    @SerializedName("Latitude")
    private double latitude;
    @SerializedName("Longitude")
    private double longtitude;

    @SerializedName("Capacity")
    private int capital;
    private List<Category> categories;
    @SerializedName("Services")
    private List<Service> services;
    @SerializedName("WorkingHours")
    private List<WorkingHour> workingHours;
    private String grant_type;
    @SerializedName("CurrentPromotions")
    private List<Promotion> promotions;
    private List<Comment> reviews;




    public Salon() {
    }

    public Salon(String name, String address, String email, String phone, int capital) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.capital = capital;
    }

    public Salon(String name, String address, String email, String phone, double latitude, double longtitude, int capital) {
        this.name = name;
        this.address = address;
        this.email = email;
        this.phone = phone;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.capital = capital;
    }

    public Salon(String name, String password, String grant_type) {
        this.name = name;
        this.password = password;
        this.grant_type = grant_type;
    }

    public Salon(String name, String phone, String email, String password) {
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
    }

    public Salon(String name, String address,String username, String password, String email, String phone,String role) {
        this.name = name;
        this.address = address;
        this.username = username;
        this.password = password;
        this.email = email;
        this.phone = phone;
        this.role = role;
    }

    public Salon(String name,
                 float ratingNumber,
                 boolean full,
                 int discount,
                 List<Category> categories,
                 double latitude,
                 double longtitude,
                 String address,
                 int reviewsAmount,
                 String phone,
                 String email) {
        this.name = name;
        this.ratingNumber = ratingNumber;
        this.full = full;
        this.discount = discount;
        this.categories = categories;
        this.latitude = latitude;
        this.longtitude = longtitude;
        this.address = address;
        this.reviewsAmount = reviewsAmount;
        this.phone = phone;
        this.email = email;
    }

    public List<Comment> getReviews() {
        return reviews;
    }

    public void setReviews(List<Comment> reviews) {
        this.reviews = reviews;
    }

    public Boolean getForMen() {
        return isForMen;
    }

    public void setForMen(Boolean forMen) {
        isForMen = forMen;
    }

    public Boolean getForWomen() {
        return isForWomen;
    }

    public void setForWomen(Boolean forWomen) {
        isForWomen = forWomen;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getLoggedOn() {
        return LoggedOn;
    }

    public void setLoggedOn(String loggedOn) {
        LoggedOn = loggedOn;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getGrant_type() {
        return grant_type;
    }

    public void setGrant_type(String grant_type) {
        this.grant_type = grant_type;
    }

    public void setSalonId(int salonId) {
        this.salonId = salonId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getSalonId() {
        return salonId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public float getRatingNumber() {
        return ratingNumber;
    }

    public void setRatingNumber(float ratingNumber) {
        this.ratingNumber = ratingNumber;
    }

    public boolean isFull() {
        return full;
    }

    public void setFull(boolean full) {
        this.full = full;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getReviewsAmount() {
        return reviewsAmount;
    }

    public void setReviewsAmount(int reviewsAmount) {
        this.reviewsAmount = reviewsAmount;
    }

    public Timestamp getStartTime() {
        return startTime;
    }

    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongtitude() {
        return longtitude;
    }

    public void setLongtitude(double longtitude) {
        this.longtitude = longtitude;
    }

    public List<Category> getCategories() {
        return categories;
    }

    public void setCategories(List<Category> categories) {
        this.categories = categories;
    }

    public List<WorkingHour> getWorkingHours() {
        return workingHours;
    }

    public void setWorkingHours(List<WorkingHour> workingHours) {
        this.workingHours = workingHours;
    }

    public LatLng getLatLng() {
        return new LatLng(latitude, longtitude);
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<Service> getServices() {
        return services;
    }

    public void setServices(List<Service> services) {
        this.services = services;
    }

    public int getCapital() {
        return capital;
    }

    public void setCapital(int capital) {
        this.capital = capital;
    }

    public List<Promotion> getPromotions() {
        return promotions;
    }

    public void setPromotions(List<Promotion> promotions) {
        this.promotions = promotions;
    }



    public class DayWorkingHour implements Serializable{
        private int dayInWeek;
        private float startHour;
        private float endHour;
        private boolean open;

        public DayWorkingHour(int dayInWeek, float startHour, float endHour, boolean open) {
            this.dayInWeek = dayInWeek;
            this.startHour = startHour;
            this.endHour = endHour;
            this.open = open;
        }
    }
}
