
var Fork = function() {
    this.state = 0;
    return this;
}

function waiting(timetoWait, cb){
    console.log("HI")
    setTimeout(function(){cb()}, timetoWait);
}

Fork.prototype.acquire = function(time,cb) {
    if(this.state==0){
        this.state=1;
        console.log("State changed");
    } else{
        console.log("Waiting "+ time);
        setTimeout(()=>{this.acquire(time*2,cb);} , time);
    }
    // zaimplementuj funkcje acquire, tak by korzystala z algorytmu BEB
    // (http://pl.wikipedia.org/wiki/Binary_Exponential_Backoff), tzn:
    // 1. przed pierwsza proba podniesienia widelca Filozof odczekuje 1ms
    // 2. gdy proba jest nieudana, zwieksza czas oczekiwania dwukrotnie
    //    i ponawia probe itd.
}

Fork.prototype.release = function() { 
    this.state = 0; 
}

var Philosopher = function(id, forks) {
    this.id = id;
    this.forks = forks;
    this.f1 = id % forks.length;
    this.f2 = (id+1) % forks.length;
    return this;
}

Philosopher.prototype.startNaive = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;

    //for(var i=0; i<count; i++){
        forks[f1].acquire(function(){
            forks[f2].acquire(function(){
                var max= 10;
                var eatingTime = Math.ceil(Math.random()*max);
                console.log("Philosoph "+ id + " eats for " + eatingTime + " second");
                setTimeout(function(){
                    console.log("Philosoph "+ id + " finished eating");
                    forks[f1].release();
                    forks[f2].release();
                },eatingTime*1000);
            });
        }
        );
        
    //}
    
    // zaimplementuj rozwiazanie naiwne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
}

Philosopher.prototype.startAsym = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    
    // zaimplementuj rozwiazanie asymetryczne
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
}

Philosopher.prototype.startConductor = function(count) {
    var forks = this.forks,
        f1 = this.f1,
        f2 = this.f2,
        id = this.id;
    
    // zaimplementuj rozwiazanie z kelnerem
    // kazdy filozof powinien 'count' razy wykonywac cykl
    // podnoszenia widelcow -- jedzenia -- zwalniania widelcow
}


var N = 5;
var forks = [];
var philosophers = []
for (var i = 0; i < N; i++) {
    forks.push(new Fork());
}

for (var i = 0; i < N; i++) {
    philosophers.push(new Philosopher(i, forks));
}

//for (var i = 0; i < N; i++) {
   // philosophers[i].startNaive(10);
//}
console.log(philosophers[2].forks[philosophers[2].f2] == philosophers[3].forks[philosophers[3].f1]);
philosophers[2].forks[philosophers[2].f2].acquire(
    1000, philosophers[3].forks[philosophers[3].f1].acquire(5000, function(){})
);

console.log(philosophers[2].forks[philosophers[2].f2] == philosophers[3].forks[philosophers[3].f1]);
