package com.promotioner.main.Utils;

import com.amap.api.maps2d.AMapUtils;
import com.amap.api.maps2d.model.LatLng;

/**
 * Created by chen on 14-8-20.
 */
public class MapUtils {

    //返回一中心点为中心  北方dist距离的纬度
    public static String northLon(String lat,String lon, String dist)
    {
        double Latitude = Double.parseDouble(lat);
        double Longitude = Double.parseDouble(lon);
        float distance = Float.parseFloat(dist);
        return calculateNorthLon(Latitude,Longitude,distance);
    }

    //根据中心点的经纬度，通过计算多余中心点位置0.005地方的距离，通过比值计算出自定义距离的纬度
    private static String calculateNorthLon(double lat, double lon, float distance){
        float length = AMapUtils.calculateLineDistance(new LatLng(lat,lon), new LatLng(lat,(lon + 0.005)));
        return (((distance * 0.005) / length) + lon)+"";
    }


    //返回一中心点为中心  北方dist距离的纬度
    public static String southLon(String lat,String lon, String dist)
    {
        double Latitude = Double.parseDouble(lat);
        double Longitude = Double.parseDouble(lon);
        float distance = Float.parseFloat(dist);
        return calculateSouthLon(Latitude,Longitude,distance);
    }

    //根据中心点的经纬度，通过计算多余中心点位置0.005地方的距离，通过比值计算出自定义距离的纬度
    private static String calculateSouthLon(double lat, double lon, float distance){
        float length = AMapUtils.calculateLineDistance(new LatLng(lat,lon), new LatLng(lat,(lon - 0.005)));
        return (lon - ((distance * 0.005) / length))+"";
    }

    //返回一中心点为中心  北方dist距离的经度
    public static String eastLat(String lat,String lon, String dist)
    {
        double Latitude = Double.parseDouble(lat);
        double Longitude = Double.parseDouble(lon);
        float distance = Float.parseFloat(dist);
        return calculateEastLat(Latitude,Longitude,distance);
    }

    //根据中心点的经纬度，通过计算多余中心点位置0.005地方的距离，通过比值计算出自定义距离的经度
    private static String calculateEastLat(double lat, double lon, float distance){
        float length = AMapUtils.calculateLineDistance(new LatLng(lat,lon), new LatLng((lat + 0.005), lon));
        return (((distance * 0.005) / length)+lat)+"";
    }

    //返回一中心点为中心  北方dist距离的经度
    public static String westLat(String lat,String lon, String dist)
    {
        double Latitude = Double.parseDouble(lat);
        double Longitude = Double.parseDouble(lon);
        float distance = Float.parseFloat(dist);
        return calculateWestLat(Latitude,Longitude,distance);
    }

    //根据中心点的经纬度，通过计算多余中心点位置0.005地方的距离，通过比值计算出自定义距离的经度
    private static String calculateWestLat(double lat, double lon, float distance){
        float length = AMapUtils.calculateLineDistance(new LatLng(lat,lon), new LatLng((lat - 0.005), lon));
        return (lat - ((distance * 0.005) / length))+"";
    }
}
