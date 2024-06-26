
---
# Trabajo de Fin de Grado #
## :books: Curso 2023-2024, Grado en Ingeniería Informática, ETSIIT UGR.
### _FillPDF, Editor automático de Formularios PDF_

**Autor**
- Almudena Luque Gómez

**Directores**
- Dr Pablo García Sánchez

### Introducción
En este TFG se desarrolló un programa capaz de crear campos de formulario dentro de los documentos que hemos incluido en nuestra base de datos. El objetivo de éstos es de generar una plantilla para dicho documento que más adelante podremos cargar en el mismo programa de forma automática y rellenar estos campos con los datos que deseemos. 

La memoria se encuentra disponible para su lectura [aquí](https://github.com/almudluqgom/TFG_Generador_PDFs_Automatizados/blob/master/Memoria%20TFG%20-%20FillPDF%20-%20Almudena%20Luque%20G%C3%B3mez.pdf).


### Instalación
No es necesario ningún software extra para lanzar FillPDF. Es recomendable tener una carpeta separada para éste proyecto, debido a la generación automática de documentos que puede saturar nuestro directorio.

Se recomienda que la primera acción de este programa sea configurar la ruta de guardado como configuración inicial. 

Esta configuración inicial se realiza mediante la opción "Modificar Base de Datos" y luego seleccionando el directorio de guardado de archivos.
### Ejecución 
Para añadir un PDF a la base de datos seleccione "Modificar base de Datos" y luego seleccionar la ruta de directorio deseada.

Para crear una nueva plantilla en el programa, seleccione la opción "Añadir campos a un PDF registrado" y seleccione su PDF ya registrado.

Dentro del editor, clica y arrastra para crear rectángulos, en los cuales luego introduciremos nuestro texto. Un cuadro de texto aparecerá en el panel de la izquierda, donde podrás nombrar el campo. Guardando los cambios, quedan registrados en la base de datos para recuperar más tarde.

Para rellenar una plantilla, selecciona "Rellena un PDF" para abrir el editor, donde ahora aparecen las líneas donde pusimos nuestros cuadros de texto en el documento. Rellenando los campos del panel izquierdo el texto aparecerá en pantalla, y guardar los cambios genera un PDF en la carpeta de guardado.

#### Test

Para consultar el antes y después de los documentos, consulte la carpeta [aquí](https://github.com/almudluqgom/TFG_Generador_PDFs_Automatizados/tree/master/Tests).

