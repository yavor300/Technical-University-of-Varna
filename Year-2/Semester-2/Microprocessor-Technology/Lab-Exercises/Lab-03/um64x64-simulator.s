
@ 64-битовите числа без знак в регистри R3:R2 (Y1:Y0) и R1:R0 (Z1:Z0) да се
@ умножат и 128-битовият резултат да се върне в рег. R3:R2:R1:R0 (A:B:C:D).
@ Алгоритъм: Peter Norton, "Advanced Assembly Language", 1991, стр. 229-230

.global	um64x64
um64x64:
	STMFD	SP!,{R4-R9,LR}	@ Save used registers

	UMULL R5,R6,R3,R0			@ R3 * R0 -> R6:R5

    UMULL R0,R4,R2,R0			@ R2 * R0 -> R4:R0

    UMULL R7,R8,R2,R1			@ R2 * R1 -> R8:R7

    UMULL R9,R3,R3,R1			@ R3 * R1 -> R3:R9

    ADDS R1,R4,R5    			@ R4 + R5 -> R1

    ADCS R2,R6,R8			    @ R6 + R8 + "carry" -> R2

    ADC  R3,R3,#0			    @ R3 + "carry" -> R3

    ADDS R1,R1,R7			    @ R1 + R7 -> R1

    ADCS R2,R2,R9			    @ R2 + R9 + "carry" -> R2

    ADC	 R3,R3,#0	 	        @ R3 + "carry" -> R3
	
	LDMFD	SP!,{R4-R9,PC}	@ Restore the registers and return to the main program

@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@

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

	bl	um64x64		@ coll user subroutine

	stmfa r13!,{r0,r1,r2,r3}

	mov R0,#Stdout 		@print an initial message
	ldr R1, =R3R2R1R0		@ load address of Message1 label
	swi SWI_PrStr 		@ display message to Stdout

	mov r10, #4		@ load read counter

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
InFileName:		.asciz "input-integers.txt"
FileOpenInpErrMsg:	.asciz "Failed to open input file \n"
EndOfFileMsg:		.asciz "End of file reached! Not enough number!\n"
EnoughNumbers:		.asciz "Read 4 numbers\n"
ColonSpace:		.asciz": "
comma:			.asciz ", " 	@ comma
Colon:			.asciz ":" 	@colon - двуеточие
R3R2:			.asciz " R3:R2 = "
R1R0:			.asciz "\n R1:R0 = "
R3R2R1R0:		.asciz "\n\nOutput:\nR3:R2:R1:R0 = "
			.end
