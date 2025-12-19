(define (problem LaberintoEjemplo)
    (:domain Laberinto)

    (:objects
        A1 A2 - agent
        K1 - clau
        P1 - porta
        C11 C12 C13 C14 C15 C16 C17 C18 - celda
        C21 C22 C23 C24 C25 C26 C27 C28 - celda
        C31 C32 C33 C34 C35 C36 C37 C38 - celda
        C41 C42 C43 C44 C45 C46 C47 C48 - celda
        C51 C52 C53 C54 C55 C56 C57 C58 - celda
    )

    (:init
        ;; Posición inicial del agente
        (en A1 C45)
        (en A2 C23)

        ;; Adyacencias
        (adjacent C11 C12) (adjacent C12 C13) (adjacent C13 C14) (adjacent C14 C15) (adjacent C15 C16) (adjacent C16 C17) (adjacent C17 C18)
        (adjacent C21 C22) (adjacent C22 C23) (adjacent C23 C24) (adjacent C24 C25) (adjacent C25 C26) (adjacent C26 C27) (adjacent C27 C28)
        (adjacent C31 C32) (adjacent C32 C33) (adjacent C33 C34) (adjacent C34 C35) (adjacent C35 C36) (adjacent C36 C37) (adjacent C37 C38)
        (adjacent C41 C42) (adjacent C42 C43) (adjacent C43 C44) (adjacent C44 C45) (adjacent C45 C46) (adjacent C46 C47) (adjacent C47 C48)
        (adjacent C51 C52) (adjacent C52 C53) (adjacent C53 C54) (adjacent C54 C55) (adjacent C55 C56) (adjacent C56 C57) (adjacent C57 C58)
        
        (adjacent C11 C21) (adjacent C12 C22) (adjacent C13 C23) (adjacent C14 C24) (adjacent C15 C25) (adjacent C16 C26) (adjacent C17 C27) (adjacent C18 C28)
        (adjacent C21 C31) (adjacent C22 C32) (adjacent C23 C33) (adjacent C24 C34) (adjacent C25 C35) (adjacent C26 C36) (adjacent C27 C37) (adjacent C28 C38)
        (adjacent C31 C41) (adjacent C32 C42) (adjacent C33 C43) (adjacent C34 C44) (adjacent C35 C45) (adjacent C36 C46) (adjacent C37 C47) (adjacent C38 C48)
        (adjacent C41 C51) (adjacent C42 C52) (adjacent C43 C53) (adjacent C44 C54) (adjacent C45 C55) (adjacent C46 C56) (adjacent C47 C57) (adjacent C48 C58)
        

        ;; Paredes
        (es-paret C11) (es-paret C12) (es-paret C13) (es-paret C14) (es-paret C15) (es-paret C16) (es-paret C17) (es-paret C18)
        (es-paret C21) (es-paret C27) (es-paret C28)
        (es-paret C31) (es-paret C36) (es-paret C38)
        (es-paret C41) (es-paret C48)
        (es-paret C51) (es-paret C52) (es-paret C53) (es-paret C54) (es-paret C55) (es-paret C56) (es-paret C57) (es-paret C58)

        ;; Puerta y llave
        (es-porta C47)          ; la puerta está en la celda C2
        (puerta-llave P1 K1)    ; puerta P1 se abre con K1
        (clau-en K1 C32)        ; la llave está en C1
        ;; La puerta empieza cerrada
        ;; (oberta P1) no incluida
    )

    (:goal
        ;; El agente llega a la celda de salida
        (or (en A2 C37) (en A1 C37))
    )
)
