	.section	__TEXT,__text,regular,pure_instructions
	.build_version macos, 10, 14
	.globl	_main                   ## -- Begin function main
	.p2align	4, 0x90
_main:                                  ## @main
	.cfi_startproc
## %bb.0:
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset %rbp, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register %rbp
	movb	$1, %al
	movl	$0, -4(%rbp)
	movl	_a(%rip), %ecx
	cmpl	_b(%rip), %ecx
	movb	%al, -9(%rbp)           ## 1-byte Spill
	je	LBB0_4
## %bb.1:
	xorl	%eax, %eax
	movb	%al, %cl
	movl	_c(%rip), %eax
	cmpl	_d(%rip), %eax
	movb	%cl, -10(%rbp)          ## 1-byte Spill
	je	LBB0_3
## %bb.2:
	movl	_e(%rip), %eax
	cmpl	-8(%rbp), %eax
	setle	%cl
	movb	%cl, -10(%rbp)          ## 1-byte Spill
LBB0_3:
	movb	-10(%rbp), %al          ## 1-byte Reload
	movb	%al, -9(%rbp)           ## 1-byte Spill
LBB0_4:
	movb	-9(%rbp), %al           ## 1-byte Reload
	andb	$1, %al
	movzbl	%al, %ecx
	movl	%ecx, -8(%rbp)
	movl	-4(%rbp), %eax
	popq	%rbp
	retq
	.cfi_endproc
                                        ## -- End function
	.globl	_a                      ## @a
.zerofill __DATA,__common,_a,4,2
	.section	__DATA,__data
	.globl	_b                      ## @b
	.p2align	2
_b:
	.long	1                       ## 0x1

	.globl	_c                      ## @c
	.p2align	2
_c:
	.long	2                       ## 0x2

	.globl	_d                      ## @d
	.p2align	2
_d:
	.long	3                       ## 0x3

	.globl	_e                      ## @e
	.p2align	2
_e:
	.long	4                       ## 0x4


.subsections_via_symbols
