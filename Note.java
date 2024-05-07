public class Note {
    private int number; // 0...127
    private int velocity; // 1...127
    private int startTime; // ms
    private int endTime; // ms

    public Note() { }

    public Note(int number, int velocity, int startTime, int endTime) {
        this.number = number;
        this.velocity = velocity;
        this.startTime = startTime;
        this.endTime = endTime;
    }

    public int getNumber (){
        return this.number;
    }

    public int getVelocity (){
        return this.velocity;
    }

    public int getStartTime (){
        return this.startTime;
    }

    public int getEndTime (){

        return this.endTime;
    }

}
