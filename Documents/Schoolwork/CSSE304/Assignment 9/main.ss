; <expression>                   ::= <constant>
;                                  | <variable>
;                                  | (quote <datum>)
;                                  | (lambda <formals> <expression> <expression>*)
;                                  | (if <expression> <expression> <expression>)
;                                  | <application>
; <variable>                     ::= <lexically-addressed-variable>
;                                  | <free-variable>
; <lexically-addressed-variable> ::= (<symbol> : <number> <number>)
; <free-variable>                ::= (<symbol> free)
; <constant>                     ::= <boolean> | <number> | <character> | <string>
; <formals>                      ::= <variable>
;                                  | (<variable>*)
;                                  | (<variable> <variable>* . <variable>)
; <application>                  ::= (<expression> <expression>*)

(load "interpreter.ss")
(load "functional-utils.ss")

(define (rl) (load "main.ss"))

(define (rep)
  (begin
    (display "--> ")
    (write (eval-expression
             (lexical-address
               (syntax-expand
                 (parse-expression
                   (read))))
             (lexically-addressed-environment '())))
    (newline)
    (rep)))

(define-syntax return-first
  (syntax-rules ()
    [(_) '()]
    [(_ e) e]
    [(_ e1 e2 e3 ...)
      (let ([a e1])
        (begin e2 e3 ... a))]))

(define-syntax for
  (syntax-rules ()
    [(_ (e1 _ e2 _ e3) e4)
      (begin e1
        (letrec ([helper
          (lambda ()
            (if e2
              (begin e4 e3 (helper))))])
            (helper)))]))