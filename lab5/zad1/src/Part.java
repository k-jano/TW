class Part{
    int Xstart;
    int Ystart;
    int Xstep;
    int Ystep;
    int[] array;

    Part(int Xstart, int Ystart, int Xstep, int Ystep, int[] array){
        this.Xstart=Xstart;
        this.Ystart=Ystart;
        this.Xstep=Xstep;
        this.Ystep=Ystep;
        this.array= array;
    }
}