/**Calculates the score of each player's hand
 * 
 * @author Pritish Kalra
 *
 */
public class ScoreCalculator{

 private Card one;
 private Card two;
 private Card [] phand=new Card[7];
 private Card [] modifiedHand=new Card[7];
 private Card [] activeCards=new Card[5];
 private int highCard=0;

 public ScoreCalculator(){
 
 }
 
 public ScoreCalculator (Hand aHand){

  this.phand =  aHand.getHand().toArray(new Card[aHand.getHand().size()]);
  this.modifiedHand = aHand.getHand().toArray(new Card[aHand.getHand().size()]);
  this.one = aHand.getHand().get(0);
  this.two = aHand.getHand().get(1);
 }
 private int getMax()
 {
  return Math.max(getRank(this.one),getRank(this.two));
 }


 private static int getRank(Card aCard)
 {
  Card r=aCard;
  String s=aCard.getValue();
  if( r.getValue().equals("J"))
  {return 11;}
  if (r.getValue().equals("Q"))
  {return 12;}
  if(r.getValue().equals("K"))
  {return 13;}
  if(r.getValue().equals("A"))
  {return 14;}

  int rank=Integer.parseInt(s);
  return rank;
 }

 private static Card[] sortByRank( Card[] h )
 {

  Card[] sorted=h;
  int i, j, min;

  for ( i = 0 ; i < sorted.length ; i ++ )
  {
   min = i;  
   for ( j = i+1 ; j < sorted.length ; j++ )
   {
    if ( getRank(sorted[j]) < getRank(sorted[min]))
    {
     min = j;   
    }
   }

   Card help = sorted[i];
   sorted[i] = sorted[min];
   sorted[min] = help;
  }
  return sorted;
 }

 private void activeArraySf(Card [] modifiedHand,int start,int end)
 {
  int start1=start;
  for(int i=0;i<5;i++)
  {
   this.activeCards[i]=modifiedHand[start1];
   start1++;

  }
  highCard=getRank(activeCards[4]);
 }
 private Card[] checkForDuplicates(Card[] ranked1)
 {
  Card temp;
  int k=0;
  Card[] checked=ranked1;

  while(k<2)
  {
   for(int i=0;i+1<7;i++)
   {
    if(getRank(checked[i])==getRank(checked[i+1]))
    {
     temp=checked[i+1];
     int j=i+1;
     while(j+1!=7)
     {
      checked[j]=checked[j+1];
      j++;
     }
     checked[6]=temp;
    }
   }
   k++;
  }

  return checked;
 }


 /*METHODS TO CHECK IF THE PLAYER HAS THE SPECIFIC POKER HAND 
The following methods test whether player has the specific poker hands that are in the hierarchy of poker hands in TexasHoldEm poker*/

 private boolean isStraight()
 {

  Card [] ranked=sortByRank(modifiedHand);
  Card [] ranked1=ranked;
  ranked=checkForDuplicates(ranked1);

  boolean s1=false;
  boolean s2=false;
  boolean s3=false;
  boolean a1=true;
  boolean a2=true;
  boolean a3=true;
  int j=0;
  int k=1;
  int l=2;
  boolean c1=false;
  boolean c2=false;
  boolean c3=false;


  while(a1==true && j<4)
  {
   if(getRank(ranked[j+1])!=getRank(ranked[j])+1)
   {
    a1=false;
   }
   j++;
  }

  while(a2==true && k<5)
  {
   if(getRank(ranked[k+1])!=getRank(ranked[k])+1)
   {
    a2=false;
   }
   k++;
  }

  while(a3==true && l<6)
  {
   if(getRank(ranked[l+1])!=getRank(ranked[l])+1)
   {
    a3=false;
   }
   l++;
  }


  s3=a3;
  s2=a2;
  s1=a1;

  if(s3==true)
  {
   for(int o=2;o<7;o++)
   {
    if(ranked[o].equals(this.one)||ranked[o].equals(this.two))
    {
     c3=true;
    }
   }
   if(c3==true)
   {
    activeArraySf(ranked,2,7);
    return true;
   }
  }

  if(s2==true)
  {
   for(int n=1;n<6;n++)
   {
    if(ranked[n].equals(this.one)||ranked[n].equals(this.two))
    {
     c2=true;
    }
   }
   if(c2==true)
   {
    activeArraySf(ranked,1,6);
    return true;
   }
  }

  if(s1==true)
  {
   for(int i=0;i<5;i++)
   {
    if(ranked[i].equals(this.one)||ranked[i].equals(this.two))
    {
     c1=true;
    }
   }
   if(c1==true)
   {
    activeArraySf(ranked,0,5);
    return true;
   }
  }

  return false;
 }

 private boolean isStraightFlush()
 {
  if (isStraight() && isFlush()) 
  {
   for(int i=0;i<4;i++)
   {
    if(!this.activeCards[i].getSuit().equals(this.activeCards[i+1].getSuit()))
    {
     return false;
    }
   }
   return true;
  }
  else
  {
   return false;
  }
 }

 private boolean isFlush()
 {
  int i=0;
  int count=0;
  int countOne=0;
  while(i!=7)
  {
   if( phand[0].getSuit().equals(phand[i].getSuit()) )
   {
    count++;
   }


   if( phand[1].getSuit().equals(phand[i].getSuit()))
   {
    countOne++;
   }

   i++;
  }
  if (count>=5||countOne>=5)
  {
   if(!isStraight())
   {
    highCard=getMax();
   }
   return true;
  }

  return false;

 }

 private boolean isRoyalFlush() {
  if (isFlush() == false || isStraight() == false || isStraightFlush() == false) 
  {
   return false;
  }

  boolean royalFlushPresent=false;

  royalFlushPresent=((getRank(this.activeCards[0])==10)&&(getRank(this.activeCards[1])==11)
    &&(getRank(this.activeCards[2])==12)&&(getRank(this.activeCards[3])==13)
    &&(getRank(this.activeCards[4])==14));
  return royalFlushPresent;
 }

 private boolean isFourOfAKind()
 {
  boolean a1=true;
  boolean a2=true;
  boolean a3=true;
  boolean a4=true;


  int i=0;
  int j=1;
  int k=2;
  int l=3;
  Card[] ranked=sortByRank(modifiedHand);




  while(a1==true && i<3)
  {
   if(getRank(ranked[i])!=getRank(ranked[i+1]))
   {
    a1=false;
   }
   i++;
  }

  while(a2==true && j<4)
  {
   if(getRank(ranked[j])!=getRank(ranked[j+1]))
   {
    a2=false;
   }
   j++;
  }

  while(a3==true && k<5)
  {
   if(getRank(ranked[k])!=getRank(ranked[k+1]))
   {
    a3=false;
   }
   k++;
  }

  while(a4==true && l<6)
  {
   if(getRank(ranked[l])!=getRank(ranked[l+1]))
   {
    a4=false;
   }
   l++;
  }


  if(a1==true)
  {
   highCard=getRank(ranked[0]);
   for(int m=0;m<4;m++)
   {
    if(ranked[m].equals(this.one)||ranked[m].equals(this.two))
    {
     return true;
    }
   }
  }

  if(a2==true)
  {
   highCard=getRank(ranked[1]);
   for(int n=1;n<5;n++)
   {
    if(ranked[n].equals(this.one)||ranked[n].equals(this.two))
    {
     return true;
    }
   }
  }

  if(a3==true)

  {
   highCard=getRank(ranked[2]);
   for(int o=2;o<6;o++)
   {
    if(ranked[o].equals(this.one)||ranked[o].equals(this.two))
    {
     return true;
    }
   }
  }

  if (a4==true)
  {
   highCard=getRank(ranked[3]);
   for(int p=3;p<7;p++)
   {
    if(ranked[p].equals(this.one)||ranked[p].equals(this.two))
    {
     return true;
    }
   }
  }

  return false;


 }

 private boolean isThreeOfAKind()
 {
  if(isFourOfAKind())
  {
   return false;
  }
  boolean a1=true;
  boolean a2=true;
  boolean a3=true;
  boolean a4=true;
  boolean a5=true;


  int i=0;
  int j=1;
  int k=2;
  int l=3;
  int m=4;
  Card[] ranked=sortByRank(modifiedHand);

  while(a1==true && i<2)
  {
   if(getRank(ranked[i])!=getRank(ranked[i+1]))
   {
    a1=false;
   }
   i++;
  }

  while(a2==true && j<3)
  {
   if(getRank(ranked[j])!=getRank(ranked[j+1]))
   {
    a2=false;
   }
   j++;
  }

  while(a3==true && k<4)
  {
   if(getRank(ranked[k])!=getRank(ranked[k+1]))
   {
    a3=false;
   }
   k++;
  }

  while(a4==true && l<5)
  {
   if(getRank(ranked[l])!=getRank(ranked[l+1]))
   {
    a4=false;
   }
   l++;
  }

  while(a5==true && m<6)
  {
   if(getRank(ranked[m])!=getRank(ranked[m+1]))
   {
    a5=false;
   }
   m++;
  }


  if (a5==true)
  {
   highCard=getRank(ranked[4]);
   for(int q=4;q<7;q++)
   {
    if(ranked[q].equals(this.one)||ranked[q].equals(this.two))
    {
     return true;
    }
   }
  }

  if (a4==true)
  {
   highCard=getRank(ranked[3]);
   for(int p=3;p<6;p++)
   {
    if(ranked[p].equals(this.one)||ranked[p].equals(this.two))
    {
     return true;
    }
   }
  }

  if(a3==true)
  {
   highCard=getRank(ranked[2]);
   for(int o=2;o<5;o++)
   {
    if(ranked[o].equals(this.one)||ranked[o].equals(this.two))
    {
     return true;
    }
   }
  }

  if(a2==true)
  {
   highCard=getRank(ranked[1]);
   for(int n=1;n<4;n++)
   {
    if(ranked[n].equals(this.one)||ranked[n].equals(this.two))
    {
     return true;
    }
   }
  }

  if(a1==true)
  {
   highCard=getRank(ranked[0]);
   for(int r=0;r<3;r++)
   {
    if(ranked[r].equals(this.one)||ranked[r].equals(this.two))
    {
     return true;
    }
   }
  }

  return false;
 }

 private boolean isTwoPair()
 {
  if (isThreeOfAKind()||isFourOfAKind())
  {
   return false;
  }
  Card[] ranked=sortByRank(modifiedHand);
  int one=0;
  int two=0;
  boolean firstPair=false;
  boolean secondPair=false;

  for(int i=0;i<7;i++)
  {
   if(ranked[i].equals(this.one))
   {
    one=i;
   }

   if(ranked[i].equals(this.two))
   {
    two=i;
   }
  } 

  for(int i=0;i<ranked.length;i++)
  {
   if(getRank(ranked[i])==(getRank(this.one)) && i!=one)
   {
    firstPair=true;
   }
   if(getRank(ranked[i])==(getRank(this.two)) && i!=two)
   {
    secondPair=true;
   }
  }

  if(firstPair && secondPair)
  {
   highCard=getMax();
   return true;
  }

  return false;
 }

 private boolean isOnePair()
 {
  if (isThreeOfAKind()||isFourOfAKind()||isTwoPair())
  {
      return false;
     }
  int count=0;


  Card[] array=this.phand;

  for(int i=1;i<array.length;i++)
  {
   if(getRank(this.one)==getRank(array[i]))
   {
    highCard=getRank(this.one);
    count++;
   }
  }

  for(int j=0;j<array.length;j++)
  {
   if(getRank(this.two)==getRank(array[j]) && !array[j].equals(this.two))
   {
    highCard=getRank(this.two);
    count++;
   }
  }


  if(count==1)
  {
   return true;
  }
  return false;
 }

 private boolean isHighCard()
 {
  highCard=getMax();

  return true;

 }
 /*allows to pinpoint which texas hold em poker hand best applies to the player's
  hand and accordingly returns strength of the hand*/

 public int obtainStrengthOfHand()
 {
  int strength = 0;
  if (isHighCard()) 
  {
   strength = 100 + highCard;
  }

  if (isOnePair()) 
  {
   strength = 200 + highCard;
  }
  if (isTwoPair()) 
  {
   strength = 300 + highCard;
  }
  if (isThreeOfAKind())
  {
   strength = 400 + highCard;
  }

  if (isStraight())
  {
   strength = 500 + highCard;
  }
  if (isFlush()) 
  {
   strength = 600 + highCard;
  }
  if (isFourOfAKind()) 
  {
   strength = 700 + highCard;
  }
  if (isStraightFlush())
  {
   strength = 800 + highCard;
  }
  if (isRoyalFlush()) 
  {
   strength = 900 + highCard;
  }

  return strength;
 }

 public String getStrength()
 {
  return "SCORE:" + String.valueOf(obtainStrengthOfHand());
 }
}






