	.file	"hello.c"
	.text
	.comm	n,4,4
	.comm	count,4,4
	.section	.rodata
.LC0:
	.string	"Hello"
.LC1:
	.string	"World %d\n"
	.text
	.globl	main
	.type	main, @function
main:
.LFB0:
	.cfi_startproc
	pushq	%rbp
	.cfi_def_cfa_offset 16
	.cfi_offset 6, -16
	movq	%rsp, %rbp
	.cfi_def_cfa_register 6
	leaq	.LC0(%rip), %rdi
	movl	$0, %eax
	call	printf@PLT
  movl  $24,count
  movl  $5,n
	movl	count(%rip), %edx
	movl	n(%rip), %eax
	addl	%edx, %eax
	movl	%eax, count(%rip)
	movl	count(%rip), %eax
	movl	%eax, %esi
	leaq	.LC1(%rip), %rdi
	movl	$0, %eax
	call	printf@PLT
	movl	$0, %eax
	popq	%rbp
	.cfi_def_cfa 7, 8
	ret
	.cfi_endproc
.LFE0:
	.size	main, .-main
	.ident	"GCC: (GNU) 8.2.1 20180831"
	.section	.note.GNU-stack,"",@progbits
