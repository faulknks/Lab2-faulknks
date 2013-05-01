(define lexical-address
  (lambda (expr)
    (begin
      (define apply-procs-to-parallel-list
        (lambda (plist lat)
          (cond
            ((null? plist) '())
            ((list? (car plist))
             (cons (apply-procs-to-parallel-list (car plist)
                                                 (car lat))
                   (apply-procs-to-parallel-list (cdr plist)
                                                 (cdr lat))))
            (else (cons ((car plist) (car lat))
                        (apply-procs-to-parallel-list (cdr plist)
                                                      (cdr lat)))))))
      (define id
        (lambda (x) x))

      (define increase-depth
        (lambda (env)
          (cond
            ((null? env) '())
            (else (cons (apply-procs-to-parallel-list
                          (list id id add1 id)
                          (car env))
                        (increase-depth (cdr env)))))))

      (define extend-env
        (lambda (env vars)
          (letrec
            ((E (lambda (env vars position)
                  (cond
                    ((null? vars) env)
                    ((symbol? vars) (cons (cons vars
                                                (lexical-addressed-variable 0 position))
                                          env))
                    ((list? vars) (E (cons (cons (car vars)
                                                 (lexical-addressed-variable 0 position))
                                           env)
                                     (cdr vars)
                                     (add1 position)))
                    (else (cons* (cons (car vars) (lexical-addressed-variable 0 position))
                                 (cons (cdr vars) (lexical-addressed-variable 0 (add1 position)))
                                 env))))))
            (E (increase-depth env) vars 0)))))
    (letrec
      ((L-env (lambda (env)
                (lambda (expr)
                  (L expr env))))
       (L (lambda (expr env)
            (cases expression expr
                   [free-variable (id) (let [[found (assq id env)]]
                                    (if found
                                      (cdr found)
                                      (free-variable id)))]
                   [lambda-exp (frmls bodies)
                               (lambda-exp frmls (map (L-env (cases formals frmls
                                                                    [unary (param) (extend-env env (list param))]
                                                                    [param-list (params) (extend-env env params)]
                                                                    [list-with-var-args (params var-args) (extend-env env (append params (list var-args)))]))
                                                      bodies))]
                   [if-exp (condition if-true)
                           (if-exp (L condition env)
                                   (L if-true env))]
                   [if-else-exp (condition if-true if-false)
                                (if-else-exp (L condition env)
                                             (L if-true env)
                                             (L if-false env))]
                   [vector-exp (datum)
                               (vector-exp (map (L-env env) datum))]
                   [begin-exp (bodies) (begin-exp (map (L-env env) bodies))]
                   [while-exp (test-exp bodies)
                              (while-exp (L test-exp env)
                                         (map (L-env env) bodies))]
                   [app-exp (operator operands)
                            (let ([procedure (L operator env)]
                                  [args (map (L-env env) operands)])
                              (app-exp procedure args))]
                   [set!-exp (variable value)
                             (set!-exp (let* [[name (cadr variable)]
                                              [found (assq name env)]]
                                         (if found
                                           (cdr found)
                                           (free-variable name)))
                                       (L value env))]
                   [else expr]))))
      (L expr '()))))