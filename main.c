#include <stdio.h>
float n;

void printPeg(int peg );
void hanoi(int n, int fromPeg, int usingPeg, int toPeg );

void printPeg(int peg ){
if (peg == 1){printf("%s","left");} else if(peg == 2){printf("%s","center");}else{printf("%s","right");}
return ;

}

void hanoi(int n, int fromPeg, int usingPeg, int toPeg ){
if (n != 0){hanoi(n - 1, fromPeg, toPeg, usingPeg);printf("%s","Move disk from ");printPeg(fromPeg);printf("%s"," peg to ");printPeg(toPeg);printf("%s"," peg.\n");hanoi(n - 1, usingPeg, fromPeg, toPeg);}
return ;

}

int main( ){
int n = 0;
printf("%s","How many pegs? ");
scanf("%d",&n);
hanoi(n, 1, 2, 3);
return 1;

}

