
@ Да се напише подпрограма, която да връща в регистри R1:R0 абсолютната
@ стойност (модула) на разликата между двойните думи със знак, подадени в
@ регистри R1:R0 и R3:R2.

.global absdif64
absdif64:
	CMP R1,R3	 		@ Сравни старшите думи A
	BLT L1				@ Ако уаляемото е по-малко от умалителя - размени ги
	BGT L2				@ Ако умаляемото е по-голямо от умалителя - извади ги
	CMP R0,R2			@ Сравни младшите думи, защото старшите думи са равни
	BHS L2				@ Ако умаляемото е не по-малко от умалителя -  извади ги
	L1: SUBS R0,R2,R0	@ При умаляемо по-малко от умалителя: извади младшите 
		SBC R1,R3,R1	@и старшите думи на умаляемото от умалителя
	BX LR				@ край на подпрограмата и връщане в главната програма
	L2: SUBS R0,R0,R2	@ При умаляемо по-голямо от умалителя - извади младшите
		SBC R1,R1,R3	@ и старшите думи на умалителя от умаляемото
	BX LR				@ край на подпрограмата и връщане в главната програма

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

	bl	absdif64		@ coll user subroutine

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
