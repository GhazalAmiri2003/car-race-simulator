package carrace.track;

import java.util.Arrays;
import java.util.List;

public class Track {
    private final TrackType type;
    private double length;         // طول مسیر (بر حسب km)
    private int curveCount;        // تعداد پیچ‌ها
    private int pitStopCount;      // تعداد PitStop
    private double difficulty;     // ضریب سختی کلی مسیر
    private double curveSharpness; // ضریب شدت پیچ‌ها
    private List<Double> curvePositions;    // موقعیت هر پیچ (km)
    private List<Double> pitStopPositions;  // موقعیت هر پیت استاپ (km)
    private double finishLine;     // موقعیت خط پایان

    public Track(TrackType type) {
        this.type = type;
        switch (type) {
            case CITY:
                this.length = 15;
                this.curveCount = 5;
                this.pitStopCount = 3;
                this.difficulty = 1.0;
                this.curveSharpness = 0.7;
                this.curvePositions = Arrays.asList(2.5, 6.0, 9.0, 12.0, 14.0);
                this.pitStopPositions = Arrays.asList(4.0, 8.0, 12.0);
                this.finishLine = 15.0;
                break;
            case MOUNTAIN:
                this.length = 10;
                this.curveCount = 14;
                this.pitStopCount = 3;
                this.difficulty = 1.1;
                this.curveSharpness = 0.9;
                this.curvePositions = Arrays.asList(0.8, 1.3, 2.1, 2.9, 3.5, 4.0, 4.7, 5.1, 6.2, 7.1, 8.0, 8.6, 9.1, 9.7);
                this.pitStopPositions = Arrays.asList(2.5,4.6, 6.9);
                this.finishLine = 10.0;
                break;
            case ARCTIC:
                this.length = 18;
                this.curveCount = 10;
                this.pitStopCount = 4;
                this.difficulty = 1.2;
                this.curveSharpness = 1.0;
                this.curvePositions = Arrays.asList(2.0, 3.5, 5.3, 7.0, 8.5, 10.0, 12.0, 13.7, 15.0, 16.5);
                this.pitStopPositions = Arrays.asList(4.0,9.6,12.4,16.0);
                this.finishLine = 18.0;
                break;
        }
    }

    public TrackType getType() { return type; }
    public double getLength() { return length; }
    public int getCurveCount() { return curveCount; }
    public int getPitStopCount() { return pitStopCount; }
    public double getDifficulty() { return difficulty; }
    //FOR LATER USAGE

    public double getCurveSharpness() { return curveSharpness; }
    public List<Double> getCurvePositions() { return curvePositions; }
    public List<Double> getPitStopPositions() { return pitStopPositions; }
    public double getFinishLine() { return finishLine; }

    // نزدیک‌ترین پیچ بعد از موقعیت فعلی ماشین
    public double getNextCurvePosition(double currentPosition) {
        for (double pos : getCurvePositions()) {
            if (pos > currentPosition) return pos;
        }
        return getFinishLine(); // اگر پیچ جدیدی نموند، خط پایان
    }

    // نزدیک‌ترین پیت‌استاپ بعد از موقعیت فعلی ماشین
    public double getNextPitStopPosition(double currentPosition) {
        for (double pos : getPitStopPositions()) {
            if (pos > currentPosition) return pos;
        }

        return getFinishLine(); // اگر پیت‌استاپ جدیدی نموند، خط پایان
    }

    @Override          //لاگ‌گیری، نمایش متنی یا دیباگ، اطلاعات شی به‌راحتی قابل‌مشاهده باش
    public String toString() {
        return "Track: " + type +
                ", Length: " + length +
                " km, Curves: " + curveCount +
                ", PitStops: " + pitStopCount +
                ", Difficulty: " + difficulty +
                ", CurveSharpness: " + curveSharpness +
                ", CurvePositions: " + curvePositions +
                ", PitStopPositions: " + pitStopPositions +
                ", FinishLine: " + finishLine;
    }
}
