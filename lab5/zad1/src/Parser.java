import java.util.concurrent.Callable;

class Parser implements Callable {
    private int[] toReturn;
    private final int Xstart;
    private final int Ystart;
    private final int Xstep;
    private final int Ystep;
    private final int MAX_ITER;
    private final double ZOOM;
    private double zx, zy, cX, cY, tmp;

    Parser(int x, int y, int xstep, int ystep, int maxiter, double zoom){
        this.Xstart=x;
        this.Ystart=y;
        this.Xstep=xstep;
        this.Ystep=ystep;
        this.MAX_ITER=maxiter;
        this.ZOOM=zoom;
        toReturn=new int[Xstep*Ystep];
    }

    @Override
    public Part call(){
        for(int y=Ystart; y<Ystart+Ystep; y++){
            for(int x=Xstart; x<Xstart+Xstep; x++){
                zx = zy = 0;
                cX = (x - 400) / ZOOM;
                cY = (y - 300) / ZOOM;
                int iter = MAX_ITER;
                while (zx * zx + zy * zy < 4 && iter > 0) {
                    tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter--;
                }

                toReturn[x-Xstart + (y-Ystart)*Xstep] = iter | (iter << 8);
            }
        }
        return new Part(Xstart, Ystart, Xstep, Ystep, toReturn);
    }
}
