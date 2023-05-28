.global heapSort
heapSort:
  STMFDSP!,{R4-R7,LR}
  MOV R3,R1,LSR #1
  SUB R1,R1,#1

  L1: MOV R2,R3
    BL DOWNHEAP
    SUBS R3,R3,#1
    BHS L1
    BL current_array

  L2: LDR R3,[R0,R1,LSL #2]
    LDR R4,[R0]
    STR R4,[R0,R1,LSL #2]
    STR R3,[R0]
    MOV R2,#0
    SUB R1,R1,#1
    BL current_array
    BL DOWNHEAP
    BL current_array
    CMP R1,#0
    BNE L2
    LDMFDSP!,{R4-R7,PC}

  DOWNHEAP: LDR R5,[R0,R2,LSL #2]

  L3: MOV R4,R2,LSL #1
    CMP R4,R1
    ADDLT R6,R0,R4,LSL #2
    LDMLTIA R6,{R6,R7}
    CMPLT R6,R7
    ADDLT R4,R4,#1
    LDR R6,[R0,R4,LSL #2]
    CMP R5,R6
    BGE L4
    STR R6,[R0,R2,LSL #2]
    MOV R2,R4
    CMP R2,R1,LSR #1
    BLS L3

  L4: STR R5,[R0,R2,LSL #2]
    BX LR      

.global current_array
current_array:

  STMFD  SP!,{R0-R10,LR}  

  mov  r2,r1

  mov  R0,#Stdout     
  ldr  R1, =R1R0    
  swi  SWI_PrStr     

  mov  r1,r2      
  mov  R0,#Stdout     
  swi  SWI_PrInt

  mov  R0,#Stdout     
  ldr  R1, =Spaces    
  swi  SWI_PrStr     

  mov  r10,#10
  LDR  R3, =InpMas
cur_outlp:  
  LDR  R6,[R3],#4

  mov   r1,r6     
  mov   R0,#Stdout   
  swi   SWI_PrInt


  mov   R0,#Stdout   
  ldr   r1, =Space  
  swi   SWI_PrStr
  
  LDR  R6,[R3],#4

  mov   r1,r6    
  mov   R0,#Stdout   
  swi   SWI_PrInt

  mov   R0,#Stdout   
  ldr   r1, =Space
  swi   SWI_PrStr

  subs  r10,r10,#1
  bne  cur_outlp   

  LDMFD   SP!,{R0-R10,PC}  




  .equ SWI_Open, 0x66   
  .equ SWI_Close,0x68   
  .equ SWI_PrChr,0x00   
  .equ SWI_PrStr, 0x69   
  .equ SWI_PrInt,0x6b   
  .equ SWI_RdInt,0x6c   
  .equ Stdout, 1     
  .equ SWI_Exit, 0x11   
  .global _start
  .text  


_start:
  LDR  R3, =InpMas
 
  mov   R0,#Stdout     
  ldr   R1, =BegAdr    
  swi   SWI_PrStr     

  mov   r1,r3       
  mov   R0,#Stdout     
  swi   SWI_PrInt

  mov   R0,#Stdout     
  ldr   R1, =Number    
  swi   SWI_PrStr     

  mov   r1,#20      
  mov   R0,#Stdout     
  swi   SWI_PrInt


  mov   R0,#Stdout     
  ldr   R1, =MasCont    
  swi   SWI_PrStr     
  


  mov  r10,#10
  LDR  R3, =InpMas
inplp:  
  LDR  R6,[R3],#4

  mov   r1,r6       
  mov   R0,#Stdout     
  swi   SWI_PrInt


  mov   R0,#Stdout     
  ldr   r1, =Space    
  swi   SWI_PrStr
  
  LDR  R6,[R3],#4

  mov   r1,r6      
  mov   R0,#Stdout     
  swi   SWI_PrInt

  mov   R0,#Stdout     
  ldr   r1, =Space
  swi   SWI_PrStr

  subs  r10,r10,#1
  bne  inplp     



  

  LDR  R0, =InpMas
  mov  r1,#20

  bl  heapSort    





  mov R0,#Stdout     
  ldr R1, =output_array  
  swi SWI_PrStr     

  mov  r10,#10
  LDR  R3, =InpMas
outlp:  
  LDR  R6,[R3],#4

  mov   r1,r6       
  mov   R0,#Stdout     
  swi   SWI_PrInt


  mov   R0,#Stdout     
  ldr   r1, =Space    
  swi   SWI_PrStr
  
  LDR  R6,[R3],#4

  mov   r1,r6      
  mov   R0,#Stdout     
  swi   SWI_PrInt

  mov   R0,#Stdout     
  ldr   r1, =Space
  swi   SWI_PrStr

  subs  r10,r10,#1
  bne  outlp     




Exit:
  Swi SWI_Exit 
InFileError:
  mov R0, #Stdout
  ldr R1, =FileOpenInpErrMsg
  swi SWI_PrStr
  bal Exit 


      .data
      .align

InFileHandle:    .word 0
InFileName:    .asciz "input integer.txt"
FileOpenInpErrMsg:  .asciz "Failed to open input file \n"
EndOfFileMsg:    .asciz "End of file reached! Not enough number!\n"
EnoughNumbers:    .asciz "Read 1 numbers\n"
Spaces:      .asciz"           "
Space:      .asciz" "
comma:      .asciz ", "   
Colon:      .asciz ":"   
MasCont:      .asciz "\n\narray content = "
BegAdr:      .asciz "\n\nBegin address = "
Number:      .asciz "\n\nNumber of items = "
R1R0:      .asciz "\n\nR1 = "
output_array:    .asciz "\n\nOutput array  = "


InpMas:      .word  20,19,17,18,15,16,140,13,12,11,10,9,8,7,6,5,4,3,2,1
      .end
