@ Да се напише подпрограма на асемблер, към която се подават
@ две 64 битови числа без знак, в двете двойки регистри
@ R1:R0 и R3:R2 като R1 и R3 съдържат старшите части,
@ а R0 и R2 – младшите части. Подпрограмата да връща 64
@ битов резултат в двойката регистри R1:R0, като:
@ R1:R0 = 0 - ако някое от входните числа е = 0;
@ R1:R0 = R1 * R3 - ако R1:R0 = R3:R2 или R1:R0 > R3:R2
@ R1:R0 = R1:R0 + R3:R2 - ако R1:R0 < R3:R2

CarryDozens:
	CMP R0,#10
	BLT EndCarryDozens
	SUB R0,R0,#10
	ADD R1, R1, #1
	B CarryDozens

EndCarryDozens:
	BX LR

ToZero:
	SUB R0,R0,R0
	SUB R1,R1,R1
	BX LR

.global DigitComparator
DigitComparator:
	CMP R1,#0
	BEQ L1
	CMP R3,#0
	BEQ L3
	CMP R1,R3
	BEQ L5
	BGT L6
	BLT L7
	L1: CMP R0,#0
		BEQ ToZero
	L3: CMP R2,#0
		BEQ ToZero
	L5: CMP R0,R2
		BEQ L6
		BGT L6
		BLT L7
	L6: SMULL R0,R1,R1,R3
		B CarryDozens
	L7: ADDS R0,R0,R2
		ADC R1,R1,R3
		B CarryDozens

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@
@@@ OPEN INPUT FILE, READ INTEGER FROM FILE, PRINT IT, CLOSE INPUT FILE
	.equ SWI_Open, 0x66 	@ open a file
	.equ SWI_Close,0x68 	@ close a file
	.equ SWI_PrChr,0x00 	@ Write an ASCII char to Stdout
	.equ SWI_PrStr, 0x69 	@ Write a null-ending string
	.equ SWI_PrInt,0x6b 	@ Write an Integer
	.equ SWI_RdInt,0x6c 	@ Read an Integer from a file
	.equ Stdout, 1 		@ Set output target to be Stdout
	.equ SWI_Exit, 0x11 	@ Stop execution
	.global _start
	.text	
_start:

	mov r10, #5		@ read counter

@ == Open an input file for reading =============================
@ if problems, print message to Stdout and exit
	ldr r0,=InFileName 	@ set Name for input file
	mov r1,#0 		@ mode is input
	swi SWI_Open 		@ open file for input
	bcs InFileError 	@ Check Carry-Bit (C): if= 1 then ERROR
@ Save the file handle in memory:
	ldr r1,=InFileHandle 	@ if OK, load input file handle
	str r0,[r1] 		@ save the file handle

@ == Read integers until end of file =============================
RLoop:

@ check of read numbers
	subs r10, r10, #1
	beq  ReadNumbers 	 

	ldr r0,=InFileHandle 	@ load input file handle
	ldr r0,[r0]
	swi SWI_RdInt 		@ read the integer into R0
	bcs EofReached 		@ Check Carry-Bit (C): if= 1 then EOF reached

@ to stack
	stmfd r13!, {r0}

	bal RLoop 		@ keep reading till end of file

@ == End of file ===============================================
EofReached:
	mov R0, #Stdout 	@ print last message
	ldr R1, =EndOfFileMsg
	swi SWI_PrStr

@ == Close a file ===============================================
	ldr R0, =InFileHandle 	@ get address of file handle
	ldr R0, [R0] 		@ get value at address
	swi SWI_Close
	bal Exit 		@ go to end

ReadNumbers:
	mov R0, #Stdout 	@ prind a message for end of read
	ldr R1, =EnoughNumbers
	swi SWI_PrStr

@ == Close a file ===============================================
	ldr R0, =InFileHandle 	@ get address of file handle
	ldr R0, [R0] 		@ get value at address
	swi SWI_Close



@ == Read integers from stack =============================

	ldmfd r13!, {r4, r5, r6, r7}


@ R3:R2

	mov R0,#Stdout 		@ print an initial message
	ldr R1, =R3R2		@ load address of Message1 label
	swi SWI_PrStr 		@ display message to Stdout

	mov r1,r7 		@ R1 = integer to print
	mov R0,#Stdout 		@ target is Stdout
	swi SWI_PrInt

	mov R0,#Stdout 		@ print colon
	ldr r1, =Colon
	swi SWI_PrStr

	mov r1,r6 		@ R1 = integer to print
	mov R0,#Stdout 		@ target is Stdout
	swi SWI_PrInt

@ R1:R0

	mov R0,#Stdout 		@ print an initial message
	ldr R1, =R1R0		@ load address of Message1 label
	swi SWI_PrStr 		@ display message to Stdout

	mov r1,r5 		@ R1 = integer to print
	mov R0,#Stdout 		@ target is Stdout
	swi SWI_PrInt

	mov R0,#Stdout 		@ print Colon
	ldr r1, =Colon
	swi SWI_PrStr

	mov r1,r4 		@ R1 = integer to print
	mov R0,#Stdout 		@ target is Stdout
	swi SWI_PrInt



@@@@@@@@@@@@@@@@@@

	mov r0,r4 
	mov r1,r5
	mov r2,r6 
	mov r3,r7 

	bl	DigitComparator		@ coll user subroutine

	stmfa r13!,{r0,r1}

	mov R0,#Stdout 		@print an initial message
	ldr R1, =outputR1R0		@ load address of Message1 label
	swi SWI_PrStr 		@ display message to Stdout

	mov r10, #2		@ load read counter

dLoop:

@ from stack
	ldmfa r13!, {r0}

@ print the integer to Stdout
	mov r1,r0 		@ R1 = integer to print
	mov R0,#Stdout 		@ target is Stdout
	swi SWI_PrInt

@ check of read numbers
	subs r10, r10, #1
	beq  Exit 	 

	mov R0,#Stdout 		@ print new line
	ldr r1, =Colon
	swi SWI_PrStr
	bal dLoop 		@ keep reading till end of file

Exit:
	Swi SWI_Exit @ stop executing
InFileError:
	mov R0, #Stdout
	ldr R1, =FileOpenInpErrMsg
	swi SWI_PrStr
	bal Exit @ give up, go to end


			.data
			.align
@InFileHandle:		.skip 4
InFileHandle:		.word 0
InFileName:		.asciz "input integer.txt"
FileOpenInpErrMsg:	.asciz "Failed to open input file \n"
EndOfFileMsg:		.asciz "End of file reached! Not enough number!\n"
EnoughNumbers:		.asciz "Read 4 numbers\n"
ColonSpace:		.asciz": "
comma:			.asciz ", " 	@ comma
Colon:			.asciz ":" 	@colon - двуеточие
R3R2:			.asciz " R3:R2 = "
R1R0:			.asciz "\n R1:R0 = "
outputR1R0:		.asciz "\n\nOutput:\nR1:R0 = "
			.end
