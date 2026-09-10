# Resumen de Clases: Paradigma Funcional en Haskell (UTN - Paradigmas de Programación)

Este documento constituye una guía de estudio exhaustiva y pulida sobre los fundamentos del **Paradigma Funcional** y su implementación en **Haskell**, estructurada a partir de los apuntes de las clases del **20/08** y **03/09**, integrados y complementados con la bibliografía oficial de la cátedra (*Módulo 1 de Fernando Dodino y colaboradores* y los *Apuntes del CEIT de Lucas Spigariol*).

---

# PARTE I: CLASE 1 (20/08) — Fundamentos del Paradigma Funcional y Primeros Pasos en Haskell

## 1. Introducción y Filosofía del Paradigma Funcional

El paradigma funcional propone un cambio radical en la forma de construir e interpretar el software, alejándose del modelo imperativo/procedural basado en la arquitectura de Von Neumann (secuencia de órdenes, celdas de memoria mutables y estados cambiantes).

*   **Enfoque Declarativo:** A diferencia del paradigma imperativo (que enfatiza el *cómo* lograr un resultado paso a paso mediante instrucciones algorítmicas), el paradigma funcional se centra en el **qué**: se describe el problema mediante la definición de **expresiones y relaciones matemáticas puras**.
*   **La Metáfora de la Calculadora:** Un programa funcional no es un conjunto de pasos a ejecutar secuencialmente, sino una **función única y global** que toma un *input* (datos de entrada) y lo transforma en un *output* (resultado final).
*   **Unicidad de Funciones:** Tomando el rigor de la matemática, una relación entre dos conjuntos es función si respeta la condición de **unicidad**: para cada elemento del dominio (entrada) existe un único valor en la imagen (salida). En un programa funcional no existe la posibilidad de que una función devuelva resultados distintos si se le proveen los mismos argumentos.
*   **Transparencia Referencial:** Decimos que una solución posee **transparencia referencial** si cualquier expresión $E$ puede ser reemplazada por su valor de retorno $V$ en cualquier punto del programa sin alterar el resultado o comportamiento del sistema.
    *   *Efecto de lado (Side Effect):* Se produce cuando la evaluación de un bloque de código modifica un estado global o de memoria externa. El paradigma funcional puro **elimina el efecto de lado**.
    *   *Ventajas de la transparencia referencial:*
        1. **Corrección matemática y Testing simplificado:** Es infinitamente más sencillo demostrar que un programa funciona correctamente mediante demostraciones algebraicas (propiedades conmutativa, asociativa, etc.).
        2. **Optimización por Memorización (*Memoization*):** El compilador o motor puede almacenar en *cache* el resultado de una expresión costosa la primera vez que se calcula y reutilizarlo directamente en evaluaciones posteriores.
        3. **Independencia del tiempo y orden de evaluación:** No importa en qué orden ni en qué momento exacto se evalúe una expresión, el resultado será idéntico.
*   **La Variable como Incógnita Fija:** En imperativo, una variable es una "caja" o posición física de memoria cuyo contenido se sobreescribe a lo largo del tiempo. En funcional, una variable respeta la definición matemática: es una **incógnita** o un valor no resuelto. 
    *   *La regla de la No-Asignación Destructiva:* Una vez que una variable se vincula a un valor, **permanece inmutable**. Expresiones imperativas como `x = x + 1` no tienen sentido en este paradigma, ya que representan una igualdad lógica imposible ($x \neq x + 1$).
*   **Ausencia de Ciclos Imperativos (`for`, `while`):** Como los bucles imperativos dependen de modificar variables de control (contadores o banderas), en el paradigma funcional **no existen las estructuras de control repetitivas tradicionales**. Todas las iteraciones se resuelven mediante **recursividad**, **composición** y **funciones de orden superior**.

---

## 2. Entorno de Trabajo y Herramientas en Haskell

La cátedra adopta **Haskell** como lenguaje puramente funcional de propósito general.

*   **GHCup:** Herramienta oficial para instalar y gestionar el compilador de Haskell (GHC) y su consola interactiva.
*   **Archivos de Código Fuente (`.hs`):** Los programas se escriben en archivos de texto plano con extensión `.hs` (ejemplo: `clase1.hs`).
*   **Consola interactiva GHCi:** Es el entorno REPL (*Read-Eval-Print Loop*) desde donde se evalúan expresiones y se prueban las funciones.
    *   `:l <nombre_archivo.hs>` (o `:load`): Carga un archivo `.hs` en la sesión interactiva.
    *   `:r` (o `:reload`): Recarga el archivo abierto. Se utiliza cada vez que se modifican y guardan las funciones en el editor de código.
    *   `:t <expresion>` (o `:type`): Consulta el tipo de dato inferido o declarado para una variable, valor o función.
    *   `:q` (o `:quit`): Cierra la consola GHCi.

---

## 3. Tipos de Datos Primitivos

Haskell posee un sistema de tipos estático y fuertemente tipado. Los tipos primitivos o bases más comunes son:

*   **`Int`:** Números enteros de precisión fija (limitados por la palabra de la máquina, habitualmente entre $-2^{63}$ y $2^{63}-1$).
*   **`Integer`:** Números enteros de precisión arbitrariamente grande (sin límite de tamaño, limitado únicamente por la memoria disponible). Ideal para cálculos matemáticos extensos como factoriales.
*   **`Float`:** Números decimales de punto flotante de precisión simple.
*   **`Double`:** Números decimales de punto flotante de doble precisión (más preciso).
*   **`Bool`:** Valores booleanos. Sus únicos dos habitantes son las constantes `True` y `False`.
*   **`Char`:** Un único caracter. Se delimita entre comillas simples (ejemplo: `'a'`, `'Z'`, `'9'`).
*   **`String` (o `[Char]`):** Cadenas de texto. En Haskell, un `String` es textualmente un sinónimo de tipo para una **lista de caracteres** (`[Char]`). Se delimita entre comillas dobles (ejemplo: `"Hola"`).

---

## 4. Firmas y Sintaxis de Funciones

En Haskell, la definición de una función se compone de dos partes fundamentales: **la firma (o prototipo de tipos)** y **el cuerpo (o implementación)**.

### Declarándolo en el Código:
```haskell
-- 1. Firma / Declaración de tipo (Nombre :: TipoEntrada -> TipoSalida)
esMayorDeEdad :: Int -> Bool

-- 2. Cuerpo / Implementación (Nombre parametro = Expresión)
esMayorDeEdad edad = edad >= 18
```

### Notación Prefija vs. Infija
*   **Notación Prefija (por defecto para funciones):** El nombre de la función antecede a sus argumentos, separados únicamente por espacios (sin paréntesis ni comas).
    *   Ejemplo: `mod numero 2` o `max 10 20`.
*   **Notación Infija (por defecto para operadores):** El operador se coloca en medio de los dos operandos.
    *   Ejemplo: `numero `mod` 2` o `10 + 5`.
*   **Intercambio de Notación:**
    1. Un **operador infijo** se transforma en **prefijo** al encerrarlo entre paréntesis: `(+) 2 3` equivale a `2 + 3`.
    2. Una **función prefija** de dos parámetros se transforma en **infija** al encerrarla entre comillas invertidas (*backticks*): `10 `mod` 2` equivale a `mod 10 2`.

### Operador de Concatenación de Cadenas (`++`)
Para unir dos `String` (o dos listas del mismo tipo), se utiliza el operador `++`:
```haskell
saludar :: String -> String
saludar nombre = "Hola, " ++ nombre ++ "!"
```

---

## 5. Definiciones Condicionales con Guardas (`|`)

Cuando una función necesita tomar caminos alternativos según ciertas condiciones sobre sus parámetros, se utilizan **guardas**. Su equivalente en matemática son las **funciones definidas por partes o por trozos**.

```haskell
max :: Int -> Int -> Int
max x y
  | x > y     = x
  | otherwise = y
```

### Reglas de las Guardas:
1. Cada guarda comienza con el símbolo de barra vertical (`|`), seguido de una expresión booleana y el signo `=` que indica qué retornar si la condición se cumple.
2. Las condiciones se evalúan en orden **estrictamente secuencial**, de arriba hacia abajo.
3. La palabra reservada `otherwise` equivale sintácticamente a `True`. Se coloca siempre en la última guarda para capturar cualquier caso no cubierto anteriormente y garantizar la **existencia** de respuesta.

### ⚠️ Error Conceptual Común: Uso Inadecuado / Redundante de Guardas
Es un fallo muy sancionado en las evaluaciones teóricas y prácticas utilizar guardas para retornar literales booleanos cuando la condición evaluada ya es una expresión booleana en sí misma.

❌ **Incorrecto (Uso inadecuado de guardas):**
```haskell
puedoAvanzar :: String -> Bool
puedoAvanzar color
  | color == "verde" = True
  | otherwise        = False
```

✅ **Correcto (Expresión booleana directa):**
```haskell
puedoAvanzar :: String -> Bool
puedoAvanzar color = color == "verde"
```

---

## 6. Composición de Funciones (`.`)

La composición permite combinar dos o más funciones para crear una nueva función, conectando la salida de una como entrada de la otra.

Dadas $g: A \to B$ y $f: B \to C$, la composición $(f \circ g)(x) = f(g(x))$ genera una nueva función de tipo $A \to C$.

### Ejemplo en Haskell:
```haskell
sumar :: Int -> Int -> Int
sumar x y = x + y

esPar :: Int -> Bool
esPar numero = (numero `mod` 2) == 0

-- Forma 1: Evaluación anidada tradicional
funcionCompuesta :: Int -> Int -> Bool
funcionCompuesta x y = esPar (sumar x y)

-- Forma 2: Utilizando el operador de composición punto (.)
funcionCompuestaPunto :: Int -> Int -> Bool
funcionCompuestaPunto x y = (esPar . sumar x) y
```

---
---

# PARTE II: CLASE 2 (03/09) — Modelado de Datos, Tuplas, Records, Polimorfismo y Pattern Matching

## 1. Modelado de Datos y el Tipo Función

El modelado es la capacidad de abstraer entidades y fenómenos de la realidad y representarlos mediante estructuras de información en un sistema.

En el paradigma funcional, **las funciones son ciudadanos de primera clase** (*First-Class Citizens*). Esto significa que las funciones se tratan exactamente igual que cualquier otro valor: tienen un tipo de dato (de la forma `A -> B`), pueden ser asignadas a nombres, pasadas como parámetros a otras funciones o retornar desde una función.

```haskell
-- Ejemplos de tipos función:
even :: Int -> Bool
saludarA :: String -> String
concatenar :: String -> String -> String
```

---

## 2. Estructuras Compuestas I: Tuplas

Una **tupla** es una estructura de datos compuesta, heterogénea y de **tamaño fijo**. Permite agrupar varios valores de distintos tipos bajo una misma entidad.

*   Sintaxis: Se delimitan entre paréntesis y los elementos se separan por comas, ej: `("Franco", 17, True)`.
*   Su tipo de dato refleja exactamente la cantidad y tipo de sus componentes: `(String, Int, Bool)`.

```haskell
-- Ejemplo de tupla representando un estudiante: (Nombre, Edad)
type EstudianteTupla = (String, Int)

presentacion :: EstudianteTupla -> String
presentacion p = fst p ++ " tiene " ++ show (snd p) ++ " años."
```

### Proyecciones Predefinidas para Pares (2-tuplas):
Haskell incluye dos funciones nativas para trabajar con tuplas de **dos** elementos:
*   `fst :: (a, b) -> a` — Devuelve la primera componente (*first*).
*   `snd :: (a, b) -> b` — Devuelve la segunda componente (*second*).

*⚠️ Nota:* `fst` y `snd` **solo funcionan con tuplas de 2 elementos**. Para tuplas de 3 o más elementos, se debe utilizar *Pattern Matching* (desestructuración) para acceder a sus miembros:

```haskell
-- Función para obtener el primer elemento de una 3-tupla
fst3 :: (a, b, c) -> a
fst3 (x, _, _) = x

-- Función para obtener el segundo elemento de una 3-tupla
snd3 :: (a, b, c) -> b
snd3 (_, y, _) = y
```

---

## 3. Polimorfismo: Ad-Hoc vs. Paramétrico

El **polimorfismo** es la capacidad de una pieza de código para operar con valores de diferentes tipos de datos.

### A. Polimorfismo Ad-Hoc (Clases de Tipos / *Typeclasses*)
Es un polimorfismo donde existen **múltiples implementaciones o comportamientos específicos** dependiendo del tipo de dato concreto. Se implementa mediante *Typeclasses* (como `Eq` para igualdad, `Ord` para orden o `Num` para operaciones numéricas).

En el prototipo de la función se explicita mediante una **restricción de contexto**:
```haskell
-- La función esPar funciona para cualquier tipo 'a' siempre que 'a' pertenezca a la clase Integral
esPar :: Integral a => a -> Bool
esPar numero = numero `mod` 2 == 0
```

### B. Polimorfismo Paramétrico (Genéricos)
Existe **una única implementación genérica** que funciona exactamente igual para todos los tipos de datos posibles, sin hacer suposiciones sobre la estructura interna del dato. Se representa en las firmas mediante **variables de tipo** (escritas en minúscula, ej. `a`, `b`, `c`).

```haskell
-- La función id (identidad) o fst son polimórficas paramétricas:
fst :: (a, b) -> a
fst (x, _) = x
```

---

## 4. Definición de Tipos con Record Syntax (`data`)

Para crear estructuras con campos nombrados (aumentando la expresividad y evitando depender del orden de los elementos como en las tuplas), Haskell provee la sintaxis de registros mediante la palabra reservada `data`.

```haskell
-- Definición del tipo de dato Estudiante con campos nombrados (Record)
data Estudiante = UnEstudiante {
    nombre :: String,
    legajo :: Int,
    materiasQueCursa :: [String]
} deriving (Show, Eq)
```

### Creación de Instancias y Funciones Selectoras ("Getters"):
Al definir un tipo con *Record Syntax*, Haskell genera automáticamente **funciones selectoras** con el nombre de cada campo para extraer dicho valor de la estructura:

```haskell
-- Creación de un valor/instancia:
ivan :: Estudiante
ivan = UnEstudiante { nombre = "Ivan", legajo = 142123, materiasQueCursa = ["PDP", "SSL"] }

-- Consultas en GHCi (usan las funciones selectoras implícitas):
-- > legajo ivan
-- 142123
-- > nombre ivan
-- "Ivan"
```

### ⚠️ Regla de Cátedra: Colisión de Nombres en Selectoras
Dado que las selectoras de campo son funciones globales en el módulo, **no se pueden repetir los nombres de campos entre diferentes `data` dentro del mismo archivo**.

```haskell
-- ❌ ESTO CAUSA ERROR DE COMPILACIÓN (Colisión de la función 'legajo'):
data Estudiante = UnEstudiante { legajo :: Int }
data Docente    = UnDocente    { legajo :: Int }
```
*Solución:* Renombrar los campos con prefijos claros (ej. `legajoEstudiante` y `legajoDocente`).

---

## 5. Pattern Matching (Encaje de Patrones)

El **Pattern Matching** es el mecanismo fundamental del paradigma funcional para inspeccionar, validar y desestructurar (*analizar la forma interna*) de un dato.

### Comparativa: Selectoras vs. Pattern Matching en Parámetros
Podemos acceder a la información de un tipo compuesto de dos maneras:

```haskell
-- Opción 1: Utilizando la función selectora (getter)
cantidadDeMaterias :: Estudiante -> Int
cantidadDeMaterias e = length (materiasQueCursa e)

-- Opción 2: Desestructurando mediante Pattern Matching en los parámetros
cantidadDeMateriasPM :: Estudiante -> Int
cantidadDeMateriasPM (UnEstudiante _ _ materias) = length materias
```

*Ventaja del Pattern Matching:* Permite "abrir" la estructura del dato directamente en la firma de las ecuaciones de la función, extrayendo únicamente los campos necesarios e ignorando el resto con la variable anónima (`_`).

---

## 6. Tipos Variantes (Tipos Suma / Enumerados)

Los **tipos variantes** o **tipos suma** permiten modelar una entidad que puede adoptar distintas formas o alternativas estructuradas. Se definen separando los constructores con el operador `|`.

```haskell
-- Definición de un tipo variante para Figuras Geométricas
data FiguraGeometrica 
  = Circulo { radio :: Float }
  | Cuadrado { lado :: Float }
  | Rectangulo { base :: Float, altura :: Float }
  deriving (Show, Eq)

-- El comportamiento polimórfico se resuelve haciendo Pattern Matching por cada constructor:
area :: FiguraGeometrica -> Float
area (Circulo r)     = 3.14159 * r * r
area (Cuadrado l)    = l * l
area (Rectangulo b a) = b * a
```

---

## 7. Estructuras Compuestas II: Listas (`[a]`)

Una **lista** es una colección **homogénea** (todos sus elementos deben ser del mismo tipo) y de **tamaño variable**.

### Definición Inductiva y el Constructor Cons (`:`)
Internamente, una lista se define de forma recursiva:
1. Una lista es la **lista vacía** (`[]`).
2. O es un elemento (llamado **Cabeza** o *head*) enlazado a otra lista (llamada **Cola** o *tail*) mediante el constructor **cons** (`:`).

```haskell
-- La sintaxis [1, 2, 3] es azúcar sintáctico de la construcción pura:
-- 1 : (2 : (3 : []))
```

### Descomposición de Listas con Pattern Matching
Para procesar listas mediante algoritmos recursivos (inducción estructural), se descompone la lista en su cabeza (`x`) y su cola (`xs`):

```haskell
-- Estructura clásica de procesamiento inductivo sobre listas:
longitud :: [a] -> Int
longitud []     = 0                   -- Caso base: Lista vacía
longitud (x:xs) = 1 + longitud xs     -- Caso recursivo: 1 + longitud de la cola
```

---

# Resumen Integrador para Exámenes Parciales

| Concepto | Paradigma Imperativo | Paradigma Funcional (Haskell) |
| :--- | :--- | :--- |
| **Control de Flujo** | Asignación, condicionales y ciclos (`for`/`while`). | Composición, recursividad y funciones de orden superior. |
| **Estado y Variables** | Celdas de memoria mutables (`x = x + 1`). | Incógnitas matemáticas inmutables (sin asignación destructiva). |
| **Punto Focal** | Pasos e instrucciones de *cómo* computar. | Definición de expresiones de *qué* se quiere calcular. |
| **Procesamiento de Estructuras** | Modificación por índice o iteradores. | Desestructuración por *Pattern Matching* e Inducción Estructural. |
| **Modelado Complejo** | Clases, atributos mutables y métodos. | Tuplas, *Record Syntax* (`data`), y Tipos Suma Variantes. |
