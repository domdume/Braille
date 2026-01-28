<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Traductor Braille - Sistema de Traducción Bidireccional</title>
        <script src="https://cdn.tailwindcss.com"></script>
        <!-- Tesseract.js para OCR -->
        <script src="https://cdn.jsdelivr.net/npm/tesseract.js@5/dist/tesseract.min.js"></script>
        <!-- Librería para capturar el contenido del resultado tal cual se ve en pantalla -->
        <script src="https://cdnjs.cloudflare.com/ajax/libs/html2canvas/1.4.1/html2canvas.min.js"></script>
        <link rel="stylesheet" href="<%= request.getContextPath() %>/css/styles.css">
        <script>
            tailwind.config = {
                theme: {
                    extend: {
                        colors: {
                            primary: {
                                50: '#f0f9ff',
                                100: '#e0f2fe',
                                200: '#bae6fd',
                                300: '#7dd3fc',
                                400: '#38bdf8',
                                500: '#0ea5e9',
                                600: '#0284c7',
                                700: '#0369a1',
                                800: '#075985',
                                900: '#0c4a6e',
                            },
                            accent: {
                                50: '#fdf4ff',
                                100: '#fae8ff',
                                200: '#f5d0fe',
                                300: '#f0abfc',
                                400: '#e879f9',
                                500: '#d946ef',
                                600: '#c026d3',
                                700: '#a21caf',
                                800: '#86198f',
                                900: '#701a75',
                            }
                        }
                    }
                }
            }
        </script>
    </head>

    <body class="bg-gradient-to-br from-primary-50 via-white to-accent-50 min-h-screen">

        <!-- Header -->
        <header class="bg-white shadow-sm border-b border-gray-100">
            <div class="container mx-auto px-4 py-6">
                <div class="flex items-center justify-between">
                    <div class="flex items-center space-x-3">
                        <div
                            class="w-12 h-12 bg-gradient-to-br from-primary-500 to-accent-500 rounded-xl flex items-center justify-center text-white text-2xl font-bold shadow-lg">
                            ⠃
                        </div>
                        <div>
                            <h1 class="text-2xl font-bold text-gray-800">Traductor Braille</h1>
                            <p class="text-sm text-gray-500">Sistema de traducción </p>
                        </div>
                    </div>
                    <div class="hidden md:flex items-center space-x-2 text-sm text-gray-600">
                        <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                        </svg>
                        <span>Español ⟷ Braille Unicode</span>
                    </div>
                </div>
            </div>
        </header>

        <!-- Main Content -->
        <main class="container mx-auto px-4 py-8">

            <!-- Hero Section -->
            <div class="text-center mb-12 animate-fade-in">
                <h2 class="text-4xl md:text-5xl font-bold text-gray-800 mb-4">
                    Traducción Accesible
                    <span class="text-transparent bg-clip-text bg-gradient-to-r from-primary-600 to-accent-600">
                        e Inclusiva
                    </span>
                </h2>
                <p class="text-lg text-gray-600 max-w-2xl mx-auto">
                    Convierte texto entre español y Braille Unicode de forma rápida y precisa.
                    Ideal para aprendizaje, accesibilidad y comunicación inclusiva.
                </p>
            </div>

            <!-- Additional Info Section -->
            <div class="max-w-5xl mx-auto mb-12 grid md:grid-cols-2 gap-6 animate-slide-up">
                <div class="bg-white rounded-xl p-6 shadow-lg border border-gray-100">
                    <h3 class="text-lg font-bold text-gray-800 mb-3 flex items-center">
                        <svg class="w-6 h-6 text-primary-500 mr-2" fill="none" stroke="currentColor"
                            viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z">
                            </path>
                        </svg>
                        ¿Cómo usar?
                    </h3>
                    <ol class="space-y-2 text-sm text-gray-600 list-decimal list-inside">
                        <li>Selecciona la dirección de traducción</li>
                        <li>Escribe o pega tu texto</li>
                        <li>Haz clic en "Traducir"</li>
                        <li>Pulsa "Descargar PNG" para obtener la señalética</li>
                    </ol>
                    <p class="text-xs text-gray-500 mt-4">💡 Atajo: Ctrl + Enter para traducir</p>
                </div>

                <div class="bg-white rounded-xl p-6 shadow-lg border border-gray-100">
                    <h3 class="text-lg font-bold text-gray-800 mb-3 flex items-center">
                        <svg class="w-6 h-6 text-accent-500 mr-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M12 6.253v13m0-13C10.832 5.477 9.246 5 7.5 5S4.168 5.477 3 6.253v13C4.168 18.477 5.754 18 7.5 18s3.332.477 4.5 1.253m0-13C13.168 5.477 14.754 5 16.5 5c1.747 0 3.332.477 4.5 1.253v13C19.832 18.477 18.247 18 16.5 18c-1.746 0-3.332.477-4.5 1.253">
                            </path>
                        </svg>
                        Sobre Braille
                    </h3>
                    <p class="text-sm text-gray-600 leading-relaxed">
                        El sistema Braille es un método de lectura y escritura táctil utilizado por personas con
                        discapacidad visual.
                        Consiste en combinaciones de puntos en relieve que representan letras, números y símbolos.
                    </p>
                </div>
            </div>

            <!-- Translator Section -->
            <div class="bg-white rounded-3xl shadow-2xl p-8 md:p-12 max-w-5xl mx-auto animate-scale-in">
                <%--
                                <!-- Info Banner -->
                                <div
                                    class="bg-gradient-to-r from-primary-50 to-accent-50 border-l-4 border-primary-500 rounded-lg p-5 mb-8">
                                    <div class="flex items-start">
                                        <svg class="w-6 h-6 text-primary-600 mt-0.5 mr-3 flex-shrink-0" fill="none"
                                            stroke="currentColor" viewBox="0 0 24 24">
                                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                                d="M13 16h-1v-4h-1m1-4h.01M21 12a9 9 0 11-18 0 9 9 0 0118 0z"></path>
                                        </svg>

                                        <div class="text-sm text-gray-700">
                                            <p class="font-semibold mb-2">Características del Sistema:</p>
                                            <ul class="space-y-1 ml-4 list-disc">
                                                <li>Alfabeto completo con Ñ y vocales acentuadas</li>
                                                <li>Números con indicador especial</li>
                                                <li>Puntuación: . , ; : ¿ ? ¡ ! ( ) -</li>
                                                <li>Soporte para mayúsculas</li>
                                            </ul>

                        </div>
                    </div>
                </div>
--%>
                <!-- Form -->
                <form id="formTraduccion" class="space-y-6">

                    <!-- Direction Selector -->
                    <div>
                        <label class="block text-sm font-semibold text-gray-700 mb-3">
                            Dirección de traducción
                        </label>
                        <div class="grid grid-cols-1 md:grid-cols-4 gap-4">
                            <label class="relative cursor-pointer">
                                <input type="radio" name="direccion" value="ESPANOL_A_BRAILLE" checked class="peer sr-only">
                                <div class="border-2 border-gray-200 peer-checked:border-primary-500 peer-checked:bg-primary-50 rounded-xl p-4 transition-all duration-200 hover:border-primary-300 h-full">
                                    <div class="flex items-center justify-between mb-2">
                                        <span class="text-2xl">🔤</span>
                                        <svg class="w-5 h-5 text-primary-600 opacity-0 peer-checked:opacity-100" fill="currentColor" viewBox="0 0 20 20">
                                            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path>
                                        </svg>
                                    </div>
                                    <p class="font-semibold text-gray-800">Español → Braille</p>
                                    <p class="text-xs text-gray-500 mt-1">Normal</p>
                                </div>
                            </label>

                            <label class="relative cursor-pointer">
                                <input type="radio" name="direccion" value="BRAILLE_A_ESPANOL" class="peer sr-only">
                                <div class="border-2 border-gray-200 peer-checked:border-primary-500 peer-checked:bg-primary-50 rounded-xl p-4 transition-all duration-200 hover:border-primary-300 h-full">
                                    <div class="flex items-center justify-between mb-2">
                                        <span class="text-2xl">⠃</span>
                                        <svg class="w-5 h-5 text-primary-600 opacity-0 peer-checked:opacity-100" fill="currentColor" viewBox="0 0 20 20">
                                            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path>
                                        </svg>
                                    </div>
                                    <p class="font-semibold text-gray-800">Braille → Español</p>
                                    <p class="text-xs text-gray-500 mt-1">Transcripción</p>
                                </div>
                            </label>

                            <button type="button" onclick="abrirCamara()"
                                    class="border-2 border-dashed border-gray-300 rounded-xl p-4 transition-all duration-200 hover:border-accent-400 hover:bg-accent-50 flex flex-col items-center justify-center group h-full">
                                <span class="text-2xl group-hover:scale-110 transition-transform">📷</span>
                                <p class="font-semibold text-gray-800 mt-1">Usar Cámara</p>
                                <p class="text-[10px] text-gray-500 uppercase tracking-tighter">Solo Español 🇪🇸</p>
                            </button>

                            <button type="button" onclick="document.getElementById('inputArchivo').click()"
                                    class="border-2 border-dashed border-gray-300 rounded-xl p-4 transition-all duration-200 hover:border-primary-400 hover:bg-primary-50 flex flex-col items-center justify-center group h-full">
                                <span class="text-2xl group-hover:scale-110 transition-transform">📁</span>
                                <input type="file" id="inputArchivo" class="hidden" accept="image/jpeg,image/png,image/gif,image/webp" onchange="procesarArchivoImagen(event)">
                                <p class="font-semibold text-gray-800 mt-1">Subir Imagen</p>
                                <p class="text-[10px] text-gray-500 uppercase tracking-tighter">JPG, PNG, GIF, WebP</p>
                            </button>

                            <div class="col-span-1 flex flex-col gap-2">
                                <button type="button" id="btnDescargar" disabled
                                        class="w-full flex-1 border-2 border-gray-200 rounded-xl p-3 flex flex-col items-center justify-center text-primary-600 hover:border-primary-300 transition-all duration-200 opacity-50 cursor-not-allowed"
                                        onclick="descargarBraillePng()">
                                    <svg id="btnIconDescargar" class="w-6 h-6 mb-1" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                        <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v2a2 2 0 002 2h12a2 2 0 002-2v-2M7 10l5 5 5-5M12 15V3"></path>
                                    </svg>
                                    <span id="btnTextDescargar" class="font-semibold text-sm">Descargar PNG</span>
                                </button>
                                
                                <label class="flex items-center justify-center space-x-2 cursor-pointer bg-gray-50 p-2 rounded-lg border border-gray-200 hover:bg-gray-100 transition-colors">
                                    <input type="checkbox" id="checkEspejo" class="w-4 h-4 text-primary-600 rounded border-gray-300 focus:ring-primary-500">
                                    <span class="text-[10px] font-bold text-gray-600 uppercase">Modo Espejo 🪞</span>
                                </label>
                            </div>
                        </div>

                    </div>

                    <!-- Text Input -->
                    <div>
                        <label for="texto" class="block text-sm font-semibold text-gray-700 mb-3">
                            <span id="labelTexto">Texto a traducir</span>
                        </label>
                        <div class="relative">
                            <textarea id="texto" rows="6" maxlength="500"
                                class="w-full px-4 py-3 border-2 border-gray-200 rounded-xl focus:border-primary-500 focus:ring-4 focus:ring-primary-100 transition-all duration-200 resize-none text-lg"
                                placeholder="Escribe algo aquí...">Hola mundo 123</textarea>
                            <div class="absolute bottom-3 right-3 text-xs text-gray-400 bg-white px-2 py-1 rounded">
                                <span id="charCount">13</span>/500
                            </div>
                        </div>
                    </div>

                    <!-- Action Buttons -->
                    <div class="flex gap-4">
                        <button type="button" onclick="traducir()" id="btnTraducir"
                            class="flex-1 bg-gradient-to-r from-primary-500 to-accent-500 hover:from-primary-600 hover:to-accent-600 text-white font-semibold py-4 px-6 rounded-xl shadow-lg hover:shadow-xl transition-all duration-200 transform hover:-translate-y-0.5 flex items-center justify-center space-x-2">
                            <svg id="btnIcon" class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4"></path>
                            </svg>
                            <span id="btnText">Traducir</span>
                        </button>

                        <button type="button" onclick="toggleTecladoBraille()" id="btnTecladoToggle"
                            class="bg-accent-50 hover:bg-accent-100 text-accent-700 font-semibold py-4 px-6 rounded-xl border border-accent-200 transition-all duration-200 flex items-center justify-center space-x-2">
                            <span class="text-xl">⠧</span>
                            <span>Teclado Braille</span>
                        </button>

                        <button type="button" onclick="limpiar()"
                            class="bg-gray-100 hover:bg-gray-200 text-gray-700 font-semibold py-4 px-8 rounded-xl transition-all duration-200 flex items-center justify-center space-x-2">
                            <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                    d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16">
                                </path>
                            </svg>
                            <span>Limpiar</span>
                        </button>
                    </div>

                    <!-- Teclado Braille (Perkins Estilo) -->
                    <div id="tecladoBraille" class="hidden mt-6 bg-gray-50 rounded-2xl p-6 border-2 border-dashed border-gray-200 animate-slide-up">
                        <div class="text-center mb-4">
                            <h4 class="text-xs font-bold text-gray-400 uppercase tracking-widest">Entrada de Puntos (Perkins)</h4>
                            <p class="text-[10px] text-gray-500">Selecciona los puntos y pulsa 'Insertar'</p>
                        </div>
                        
                        <div class="flex flex-col items-center space-y-4">
                            <!-- Fila de Puntos -->
                            <div class="grid grid-cols-2 gap-12">
                                <div class="grid grid-cols-1 gap-4">
                                    <button type="button" data-dot="1" class="dot-btn w-12 h-12 rounded-full border-4 border-gray-300 bg-white hover:border-accent-400 transition-all flex items-center justify-center font-bold text-gray-400">1</button>
                                    <button type="button" data-dot="2" class="dot-btn w-12 h-12 rounded-full border-4 border-gray-300 bg-white hover:border-accent-400 transition-all flex items-center justify-center font-bold text-gray-400">2</button>
                                    <button type="button" data-dot="3" class="dot-btn w-12 h-12 rounded-full border-4 border-gray-300 bg-white hover:border-accent-400 transition-all flex items-center justify-center font-bold text-gray-400">3</button>
                                </div>
                                <div class="grid grid-cols-1 gap-4">
                                    <button type="button" data-dot="4" class="dot-btn w-12 h-12 rounded-full border-4 border-gray-300 bg-white hover:border-accent-400 transition-all flex items-center justify-center font-bold text-gray-400">4</button>
                                    <button type="button" data-dot="5" class="dot-btn w-12 h-12 rounded-full border-4 border-gray-300 bg-white hover:border-accent-400 transition-all flex items-center justify-center font-bold text-gray-400">5</button>
                                    <button type="button" data-dot="6" class="dot-btn w-12 h-12 rounded-full border-4 border-gray-300 bg-white hover:border-accent-400 transition-all flex items-center justify-center font-bold text-gray-400">6</button>
                                </div>
                            </div>
                            
                            <!-- Acciones del Teclado -->
                            <div class="flex gap-3 w-full max-w-xs">
                                <button type="button" onclick="insertarCaracterBraille()" class="flex-1 bg-accent-600 hover:bg-accent-700 text-white font-bold py-3 rounded-xl shadow-md transition-all">Insertar</button>
                                <button type="button" onclick="toggleMayuscula()" id="btnMayuscula" class="px-4 py-3 bg-white border border-gray-300 rounded-xl text-gray-600 hover:bg-gray-50 font-bold transition-all" title="Modo Mayúscula">⇧</button>
                                <button type="button" onclick="limpiarPuntos()" class="px-4 py-3 bg-white border border-gray-300 rounded-xl text-gray-600 hover:bg-gray-50 font-bold">X</button>
                                <button type="button" onclick="insertarEspacioBraille()" class="px-6 py-3 bg-gray-200 border border-gray-300 rounded-xl text-gray-700 hover:bg-gray-300 font-bold">␣</button>
                            </div>
                            
                            <div class="text-center">
                                <span class="text-4xl font-braille text-primary-600" id="previewBrailleChar">⠀</span>
                            </div>
                        </div>
                    </div>
                </form>

                <!-- Etiqueta de resultado (fuera de la caja) -->
                <div id="resultadoEtiqueta" class="hidden inline-flex items-center px-3 py-1 mb-2 mt-6 rounded-full bg-primary-50 border border-primary-100 text-xs font-semibold uppercase tracking-wide text-primary-700">
                    <span class="w-1.5 h-1.5 rounded-full bg-primary-500 mr-2"></span>
                    Resultado de la traducción
                </div>

                <!-- Result Section -->
                <div id="resultado" class="mt-2 hidden">
                    <div id="resultContainer" class="p-6 rounded-xl border-2">
                        <div class="flex items-start space-x-3">
                            <span id="resultEmoji" class="text-3xl"></span>
                            <div class="flex-1">
                                 <p id="resultText" class="text-2xl font-medium break-words select-all"></p>
                            </div>
                        </div>
                    </div>
                </div>

                <!--BOTON COPIAR-->
                <div class="flex justify-center mt-6">
                    <button type="button" onclick="copiar()" id="btnCopiar"
                            class="bg-gradient-to-r from-primary-500 to-accent-500 hover:from-primary-600 hover:to-accent-600 text-white font-semibold py-2 px-8 rounded-full shadow-md hover:shadow-lg transition-all duration-200 transform hover:-translate-y-0.5 flex items-center justify-center space-x-2 text-sm">
                        <svg class="w-4 h-4" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 5H6a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2v-1M8 5a2 2 0 002 2h2a2 2 0 002-2M8 5a2 2 0 012-2h2a2 2 0 012 2m0 0h2a2 2 0 012 2v3m2 4H10m0 0l3-3m-3 3l3 3"></path>
                        </svg>
                        <span id="btnTextCopiar">Copiar</span>
                    </button>
                </div>

            </div>

            <!-- Features Cards (Al final) -->
            <div class="max-w-5xl mx-auto mt-12 grid md:grid-cols-2 gap-6 animate-slide-up">
                <!-- Feature 1 -->
                <div
                    class="bg-white rounded-2xl p-6 shadow-lg hover:shadow-xl transition-all duration-300 border border-gray-100 hover:-translate-y-1">
                    <div
                        class="w-14 h-14 bg-gradient-to-br from-primary-100 to-primary-200 rounded-xl flex items-center justify-center mb-4">
                        <svg class="w-8 h-8 text-primary-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M3 5h12M9 3v2m1.048 9.5A18.022 18.022 0 016.412 9m6.088 9h7M11 21l5-10 5 10M12.751 5C11.783 10.77 8.07 15.61 3 18.129">
                            </path>
                        </svg>
                    </div>
                    <h3 class="text-xl font-bold text-gray-800 mb-2">Alfabeto Completo</h3>
                    <p class="text-gray-600">Letras A-Z, Ñ y vocales acentuadas (á, é, í, ó, ú)
                    </p>
                </div>

                <!-- Feature 2 -->
                <div
                    class="bg-white rounded-2xl p-6 shadow-lg hover:shadow-xl transition-all duration-300 border border-gray-100 hover:-translate-y-1">
                    <div
                        class="w-14 h-14 bg-gradient-to-br from-accent-100 to-accent-200 rounded-xl flex items-center justify-center mb-4">
                        <svg class="w-8 h-8 text-accent-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M7 20l4-16m2 16l4-16M6 9h14M4 15h14"></path>
                        </svg>
                    </div>
                    <h3 class="text-xl font-bold text-gray-800 mb-2">Números y Símbolos</h3>
                    <p class="text-gray-600">Números 0-9 y signos de puntuación con indicadores especiales</p>
                </div>
            </div>

        </main>

        <!-- Modal Cámara - Versión Completa con OCR y Traducción -->
        <div id="modalCamara" class="fixed inset-0 z-50 hidden flex items-center justify-center p-4 bg-black/80 backdrop-blur-sm animate-fade-in">
            <div class="bg-white rounded-3xl overflow-hidden shadow-2xl max-w-4xl w-full max-h-[90vh] overflow-y-auto">
                <div class="p-6 border-b border-gray-100 flex items-center justify-between bg-gradient-to-r from-primary-50 to-white sticky top-0 z-10">
                    <div class="flex items-center space-x-3">
                        <span class="text-2xl">📷</span>
                        <h3 class="text-xl font-bold text-gray-800">Captura y Traducción</h3>
                    </div>
                    <button onclick="cerrarCamara()" class="text-gray-400 hover:text-gray-600 transition-colors">
                        <svg class="w-8 h-8" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M6 18L18 6M6 6l12 12"></path>
                        </svg>
                    </button>
                </div>
                
                <div class="p-8">
                    <!-- Aviso sobre idioma -->
                    <div class="mb-6 bg-amber-50 border-l-4 border-amber-500 p-4 rounded">
                        <p class="text-sm font-semibold text-amber-900">ℹ️ Esta funcionalidad solo reconoce texto en español</p>
                        <p class="text-xs text-amber-800 mt-1">El OCR está configurado para extraer texto en español únicamente.</p>
                    </div>

                    <!-- Estado del proceso -->
                    <div id="estadoProceso" class="mb-6 hidden">
                        <div class="flex items-center space-x-3">
                            <div class="animate-spin rounded-full h-5 w-5 border-2 border-primary-500 border-t-transparent"></div>
                            <span id="mensajeEstado" class="text-sm font-medium text-gray-700">Inicializando...</span>
                        </div>
                    </div>

                    <!-- Sección de Captura -->
                    <div id="seccionCaptura" class="mb-8">
                        <h4 class="text-lg font-semibold text-gray-800 mb-4">Paso 1: Capturar Foto</h4>
                        <div class="relative aspect-video bg-black rounded-2xl overflow-hidden mb-4">
                            <video id="video" class="w-full h-full object-cover" autoplay playsinline></video>
                            <canvas id="canvas" class="hidden"></canvas>
                        </div>
                        <div class="flex gap-3">
                            <button onclick="capturarFoto()" id="btnCapturar"
                                    class="flex-1 bg-primary-600 hover:bg-primary-700 text-white font-bold py-3 px-6 rounded-xl shadow-lg transition-all flex items-center justify-center space-x-2">
                                <span>📸</span>
                                <span>Capturar Foto</span>
                            </button>
                            <button onclick="cerrarCamara()" 
                                    class="flex-1 bg-gray-200 hover:bg-gray-300 text-gray-700 font-bold py-3 px-6 rounded-xl transition-all">
                                Cancelar
                            </button>
                        </div>
                    </div>

                    <!-- Sección de Texto Extraído -->
                    <div id="seccionTextoExtraido" class="mb-8 hidden">
                        <h4 class="text-lg font-semibold text-gray-800 mb-4">Paso 2: Texto Extraído (Español)</h4>
                        <div class="bg-blue-50 border-2 border-blue-300 rounded-xl p-4 mb-4">
                            <p id="textoExtraido" class="text-lg font-medium text-blue-900 break-words whitespace-pre-wrap"></p>
                        </div>
                        <div class="flex gap-3">
                            <button onclick="procesarOCR()" id="btnProcesarOCR"
                                    class="flex-1 bg-accent-600 hover:bg-accent-700 text-white font-bold py-3 px-6 rounded-xl shadow-lg transition-all flex items-center justify-center space-x-2">
                                <span>✨</span>
                                <span>Traducir a Braille</span>
                            </button>
                            <button id="btnRetomar" onclick="volverACapturar()" 
                                    class="flex-1 bg-gray-200 hover:bg-gray-300 text-gray-700 font-bold py-3 px-6 rounded-xl transition-all">
                                Retomar Foto
                            </button>
                            <button id="btnSubirOtra" onclick="subirOtraImagen()" 
                                    class="flex-1 bg-gray-200 hover:bg-gray-300 text-gray-700 font-bold py-3 px-6 rounded-xl transition-all hidden">
                                Subir otra imagen
                            </button>
                        </div>
                    </div>

                    <!-- Sección de Resultado Braille -->
                    <div id="seccionResultadoBraille" class="mb-8 hidden">
                        <h4 class="text-lg font-semibold text-gray-800 mb-4">Paso 3: Traducción a Braille</h4>
                        <div class="bg-green-50 border-2 border-green-300 rounded-xl p-6">
                            <p id="textoBraille" class="text-4xl font-bold text-green-900 break-words text-center font-braille leading-relaxed" style="font-family: 'Segoe UI Symbol', 'Arial Unicode MS', sans-serif; letter-spacing: 2px;"></p>
                        </div>
                        <div class="mt-4 flex gap-3">
                            <button onclick="copiarBraille()" 
                                    class="flex-1 bg-primary-600 hover:bg-primary-700 text-white font-bold py-3 px-6 rounded-xl shadow-lg transition-all flex items-center justify-center space-x-2">
                                <svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 5H6a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2v-1M8 5a2 2 0 002 2h2a2 2 0 002-2M8 5a2 2 0 012-2h2a2 2 0 012 2m0 0h2a2 2 0 012 2v3m2 4H10m0 0l3-3m-3 3l3 3"></path>
                                </svg>
                                <span>Copiar</span>
                            </button>
                            <button onclick="cerrarCamara()" 
                                    class="flex-1 bg-gray-200 hover:bg-gray-300 text-gray-700 font-bold py-3 px-6 rounded-xl transition-all">
                                Cerrar
                            </button>
                        </div>
                    </div>

                    <!-- Sección de Errores -->
                    <div id="seccionError" class="hidden">
                        <div class="bg-red-50 border-2 border-red-300 rounded-xl p-4 mb-4">
                            <h4 class="font-semibold text-red-900 mb-2">❌ Error en el proceso</h4>
                            <p id="textoError" class="text-sm text-red-800"></p>
                        </div>
                        <button onclick="reiniciarCamara()" 
                                class="w-full bg-primary-600 hover:bg-primary-700 text-white font-bold py-3 px-6 rounded-xl transition-all">
                            Intentar de Nuevo
                        </button>
                    </div>
                </div>
            </div>
        </div>

        <!-- Footer -->
        <footer class="bg-white border-t border-gray-100 mt-16">
            <div class="container mx-auto px-4 py-6">
                <div class="text-center text-sm text-gray-600">
                    <p>Traductor Braille &copy; <%= java.time.Year.now().getValue() %> - Sistema de Traducción Accesible
                    </p>
                    <p class="mt-1 text-xs text-gray-500">Desarrollado con ❤️ para promover la accesibilidad e inclusión
                    </p>
                </div>
            </div>
        </footer>

        <script>
            // Variable global para conservar la última traducción Braille sin que se sobreescriba por mensajes
            let ultimaTraduccionBraille = '';
            let streamCamara = null;
            let puntosSeleccionados = new Set();
            let modoMayuscula = false;
            let tesseractWorker = null;
            let fotoCapturada = null;
            let esDelArchivo = false;

            // Inicialización
            document.addEventListener('DOMContentLoaded', function () {
                actualizarContador();
                actualizarPlaceholder();
                actualizarEstadoDescarga();

                // Event listeners
                document.getElementById('texto').addEventListener('input', () => {
                    actualizarContador();
                    ultimaTraduccionBraille = '';
                    actualizarEstadoDescarga();
                });
                document.querySelectorAll('input[name="direccion"]').forEach(radio => {
                    radio.addEventListener('change', () => {
                        actualizarPlaceholder();
                        ultimaTraduccionBraille = '';
                        actualizarEstadoDescarga();
                    });
                });

                // Atajo de teclado Ctrl+Enter
                document.getElementById('texto').addEventListener('keydown', function (e) {
                    if (e.ctrlKey && e.key === 'Enter') {
                        e.preventDefault();
                        traducir();
                    }
                });

                // Event listeners para botones de puntos del teclado Braille
                document.querySelectorAll('.dot-btn').forEach(btn => {
                    btn.addEventListener('click', function() {
                        const punto = parseInt(this.getAttribute('data-dot'));
                        if (puntosSeleccionados.has(punto)) {
                            puntosSeleccionados.delete(punto);
                            this.classList.remove('bg-accent-500', 'text-white', 'border-accent-600');
                            this.classList.add('bg-white', 'text-gray-400', 'border-gray-300');
                        } else {
                            puntosSeleccionados.add(punto);
                            this.classList.add('bg-accent-500', 'text-white', 'border-accent-600');
                            this.classList.remove('bg-white', 'text-gray-400', 'border-gray-300');
                        }
                        actualizarPreviewBraille();
                    });
                });
            });

            // --- Lógica Teclado Braille ---

            function toggleTecladoBraille() {
                const teclado = document.getElementById('tecladoBraille');
                teclado.classList.toggle('hidden');
                if (!teclado.classList.contains('hidden')) {
                    teclado.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
                }
            }

            function actualizarPreviewBraille() {
                const preview = document.getElementById('previewBrailleChar');
                preview.textContent = generarCaracterBraille();
            }

            function generarCaracterBraille() {
                let mask = 0;
                if (puntosSeleccionados.has(1)) mask |= 0x01;
                if (puntosSeleccionados.has(2)) mask |= 0x02;
                if (puntosSeleccionados.has(3)) mask |= 0x04;
                if (puntosSeleccionados.has(4)) mask |= 0x08;
                if (puntosSeleccionados.has(5)) mask |= 0x10;
                if (puntosSeleccionados.has(6)) mask |= 0x20;
                return String.fromCharCode(0x2800 + mask);
            }

            function insertarCaracterBraille() {
                const char = generarCaracterBraille();
                if (char === '⠀' && puntosSeleccionados.size === 0) return;
                
                const textarea = document.getElementById('texto');
                
                // Si el modo mayúscula está activo, insertar el indicador de mayúscula (⠠) antes del carácter
                if (modoMayuscula) {
                    textarea.value += '⠨';  // Indicador de mayúscula en Braille (punto 6)
                    modoMayuscula = false;  // Desactivar el modo después de usarlo
                    actualizarBotonMayuscula();
                }
                
                textarea.value += char;
                actualizarContador();
                limpiarPuntos();
            }

            function toggleMayuscula() {
                modoMayuscula = !modoMayuscula;
                actualizarBotonMayuscula();
            }

            function actualizarBotonMayuscula() {
                const btn = document.getElementById('btnMayuscula');
                if (modoMayuscula) {
                    btn.classList.remove('bg-white', 'text-gray-600', 'border-gray-300');
                    btn.classList.add('bg-accent-500', 'text-white', 'border-accent-600');
                } else {
                    btn.classList.remove('bg-accent-500', 'text-white', 'border-accent-600');
                    btn.classList.add('bg-white', 'text-gray-600', 'border-gray-300');
                }
            }

            function insertarEspacioBraille() {
                const textarea = document.getElementById('texto');
                textarea.value += String.fromCharCode(0x2800);  // ⠀ (Braille blank/espacio válido)
                actualizarContador();
            }

            function limpiarPuntos() {
                puntosSeleccionados.clear();
                document.querySelectorAll('.dot-btn').forEach(btn => {
                    btn.classList.remove('bg-accent-500', 'text-white', 'border-accent-600');
                    btn.classList.add('bg-white', 'text-gray-400', 'border-gray-300');
                });
                actualizarPreviewBraille();
            }

            // --- LÓGICA COMPLETA DE CÁMARA Y OCR ---

            function abrirCamara() {
                const modal = document.getElementById('modalCamara');
                const video = document.getElementById('video');
                modal.classList.remove('hidden');
                mostrarSeccion('seccionCaptura');
                ocultarSeccion('seccionTextoExtraido');
                ocultarSeccion('seccionResultadoBraille');
                ocultarSeccion('seccionError');
                
                navigator.mediaDevices.getUserMedia({ 
                    video: { facingMode: 'environment' }, 
                    audio: false 
                })
                .then(stream => {
                    streamCamara = stream;
                    video.srcObject = stream;
                })
                .catch(err => {
                    mostrarError('No se pudo acceder a la cámara: ' + err.message);
                });
            }

            function cerrarCamara() {
                const modal = document.getElementById('modalCamara');
                modal.classList.add('hidden');
                if (streamCamara) {
                    streamCamara.getTracks().forEach(track => track.stop());
                    streamCamara = null;
                }
                fotoCapturada = null;
            }

            function capturarFoto() {
                const video = document.getElementById('video');
                const canvas = document.getElementById('canvas');
                
                if (video.readyState !== video.HAVE_ENOUGH_DATA) {
                    mostrarError('La cámara aún no está lista. Intenta de nuevo en un momento.');
                    return;
                }

                canvas.width = video.videoWidth;
                canvas.height = video.videoHeight;
                const ctx = canvas.getContext('2d');
                ctx.drawImage(video, 0, 0);
                
                fotoCapturada = canvas.toDataURL('image/png');
                
                // Detener la cámara
                if (streamCamara) {
                    streamCamara.getTracks().forEach(track => track.stop());
                    streamCamara = null;
                }

                // Ir al paso 2: Extraer texto con OCR
                mostrarSeccion('seccionTextoExtraido');
                ocultarSeccion('seccionCaptura');
                extraerTextoOCR();
            }

            async function extraerTextoOCR() {
                mostrarEstado('Inicializando motor OCR...');
                
                try {
                    // Inicializar worker de Tesseract si no existe
                    if (!tesseractWorker) {
                        tesseractWorker = await Tesseract.createWorker('spa', 1);
                    }

                    mostrarEstado('Extrayendo texto de la imagen...');
                    const { data: { text } } = await tesseractWorker.recognize(fotoCapturada);

                    if (!text || text.trim().length === 0) {
                        mostrarEstado(null);
                        
                        // Si es de archivo, mostrar opción de subir otra imagen
                        if (esDelArchivo) {
                            alert('No se reconoció texto en la imagen. Por favor, intenta con otra imagen que tenga texto claro y visible.');
                            cerrarCamara();
                            fotoCapturada = null;
                            // Abrir selector de archivos nuevamente
                            setTimeout(() => {
                                document.getElementById('inputArchivo').click();
                            }, 100);
                        } else {
                            // Si es de cámara, volver a capturar
                            mostrarError('No se detectó texto en la imagen. Asegúrate que el texto sea visible y esté bien enfocado.');
                            volverACapturar();
                        }
                        return;
                    }

                    // Limpiar el texto
                    const textoLimpio = limpiarTexto(text);
                    
                    mostrarEstado(null);
                    document.getElementById('textoExtraido').textContent = textoLimpio;
                    
                    // Guardar el texto para el siguiente paso
                    fotoCapturada = { imagen: fotoCapturada, texto: textoLimpio };
                    
                    // Mostrar la sección de texto extraído
                    actualizarBotonesSeccionTexto();
                    mostrarSeccion('seccionTextoExtraido');

                } catch (err) {
                    console.error('Error en OCR:', err);
                    mostrarEstado(null);
                    
                    if (esDelArchivo) {
                        alert('Error al procesar la imagen: ' + err.message + '. Por favor, intenta con otra imagen.');
                        cerrarCamara();
                        fotoCapturada = null;
                        setTimeout(() => {
                            document.getElementById('inputArchivo').click();
                        }, 100);
                    } else {
                        mostrarError('Error al extraer texto: ' + err.message);
                        volverACapturar();
                    }
                }
            }

            function limpiarTexto(texto) {
                // Eliminar saltos de línea innecesarios
                texto = texto.replace(/\n+/g, ' ');
                
                // Eliminar espacios duplicados
                texto = texto.replace(/\s+/g, ' ');
                
                // Eliminar caracteres especiales no válidos (mantener letras, números, espacios, puntuación básica)
                texto = texto.replace(/[^\wáéíóúñÁÉÍÓÚÑ\s.,;:¿?¡!\-()]/g, '');
                
                // Trim
                texto = texto.trim();
                
                return texto;
            }

            async function procesarOCR() {
                if (!fotoCapturada || !fotoCapturada.texto) {
                    mostrarError('No hay texto extraído para procesar.');
                    return;
                }

                const texto = fotoCapturada.texto;
                mostrarEstado('Traduciendo a Braille...');
                ocultarSeccion('seccionTextoExtraido');

                try {
                    const url = '<%= request.getContextPath() %>/api/traducir';
                    const solicitud = {
                        texto: texto,
                        direccion: 'ESPANOL_A_BRAILLE'
                    };

                    const response = await fetch(url, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json; charset=UTF-8'
                        },
                        body: JSON.stringify(solicitud)
                    });

                    const data = await response.json();

                    if (data.exito) {
                        const textoBraille = data.textoTraducido || '';
                        document.getElementById('textoBraille').textContent = textoBraille;
                        ultimaTraduccionBraille = textoBraille;
                        actualizarEstadoDescarga();
                        
                        mostrarEstado(null);
                        mostrarSeccion('seccionResultadoBraille');
                    } else {
                        mostrarError('Error en la traducción: ' + (data.error || 'Error desconocido'));
                        volverACapturar();
                    }

                } catch (error) {
                    console.error('Error:', error);
                    mostrarError('Error de conexión: ' + error.message);
                    volverACapturar();
                }
            }

            function volverACapturar() {
                fotoCapturada = null;
                esDelArchivo = false;
                mostrarSeccion('seccionCaptura');
                ocultarSeccion('seccionTextoExtraido');
                ocultarSeccion('seccionResultadoBraille');
                
                // Reiniciar la cámara
                abrirCamara();
            }

            function subirOtraImagen() {
                fotoCapturada = null;
                esDelArchivo = true; // Mantener true porque vamos a subir otro archivo
                const modal = document.getElementById('modalCamara');
                modal.classList.add('hidden');
                document.getElementById('inputArchivo').click();
            }

            function actualizarBotonesSeccionTexto() {
                const btnRetomar = document.getElementById('btnRetomar');
                const btnSubirOtra = document.getElementById('btnSubirOtra');
                
                if (esDelArchivo) {
                    btnRetomar.classList.add('hidden');
                    btnSubirOtra.classList.remove('hidden');
                } else {
                    btnRetomar.classList.remove('hidden');
                    btnSubirOtra.classList.add('hidden');
                }
            }

            function reiniciarCamara() {
                fotoCapturada = null;
                mostrarSeccion('seccionCaptura');
                ocultarSeccion('seccionTextoExtraido');
                ocultarSeccion('seccionResultadoBraille');
                ocultarSeccion('seccionError');
                abrirCamara();
            }

            function procesarArchivoImagen(event) {
                const archivo = event.target.files[0];
                if (!archivo) return;

                const reader = new FileReader();
                reader.onload = (e) => {
                    fotoCapturada = e.target.result;
                    esDelArchivo = true;
                    
                    const modal = document.getElementById('modalCamara');
                    modal.classList.remove('hidden');
                    ocultarSeccion('seccionCaptura');
                    ocultarSeccion('seccionTextoExtraido');
                    ocultarSeccion('seccionResultadoBraille');
                    ocultarSeccion('seccionError');
                    
                    // Iniciar OCR directamente
                    extraerTextoOCR();
                };
                reader.readAsDataURL(archivo);
                
                // Limpiar el input para permitir seleccionar el mismo archivo de nuevo
                event.target.value = '';
            }

            function copiarBraille() {
                const texto = document.getElementById('textoBraille').textContent;
                if (!texto) return;

                navigator.clipboard.writeText(texto)
                    .then(() => {
                        const btn = event.target.closest('button');
                        const original = btn.innerHTML;
                        btn.innerHTML = '<svg class="w-5 h-5" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path></svg> <span>Copiado</span>';
                        setTimeout(() => {
                            btn.innerHTML = original;
                        }, 2000);
                    })
                    .catch(() => {
                        alert('Copiado correctamente al portapapeles.');
                    });
            }

            function mostrarSeccion(id) {
                document.getElementById(id).classList.remove('hidden');
            }

            function ocultarSeccion(id) {
                document.getElementById(id).classList.add('hidden');
            }

            function mostrarEstado(mensaje) {
                const estadoDiv = document.getElementById('estadoProceso');
                if (!mensaje) {
                    estadoDiv.classList.add('hidden');
                } else {
                    document.getElementById('mensajeEstado').textContent = mensaje;
                    estadoDiv.classList.remove('hidden');
                }
            }

            function mostrarError(mensaje) {
                ocultarSeccion('estadoProceso');
                ocultarSeccion('seccionCaptura');
                ocultarSeccion('seccionTextoExtraido');
                ocultarSeccion('seccionResultadoBraille');
                
                document.getElementById('textoError').textContent = mensaje;
                mostrarSeccion('seccionError');
            }

            // --- Lógica de Espejado (JS) ---

            function espejarBrailleJs(textoBraille) {
                if (!textoBraille) return textoBraille;
                const lineas = textoBraille.split('\n');
                const resultado = lineas.map(linea => {
                    let lineaEspejada = '';
                    for (let char of linea) {
                        lineaEspejada += espejarCaracterJs(char);
                    }
                    // Invertir el orden de los caracteres en la línea (para escritura por el reverso)
                    return lineaEspejada.split('').reverse().join('');
                });
                return resultado.join('\n');
            }

            function espejarCaracterJs(c) {
                const code = c.charCodeAt(0);
                // Rango Braille Unicode: 0x2800 - 0x28FF
                if (code < 0x2800 || code > 0x28FF) return c;
                
                let mask = code - 0x2800;
                let newMask = 0;
                // Mapeo de puntos:
                // Normal: 1 4  Espejo: 4 1
                //         2 5          5 2
                //         3 6          6 3
                //         7 8 (si aplica)
                if ((mask & 0x01) !== 0) newMask |= 0x08; // 1 -> 4
                if ((mask & 0x02) !== 0) newMask |= 0x10; // 2 -> 5
                if ((mask & 0x04) !== 0) newMask |= 0x20; // 3 -> 6
                if ((mask & 0x08) !== 0) newMask |= 0x01; // 4 -> 1
                if ((mask & 0x10) !== 0) newMask |= 0x02; // 5 -> 2
                if ((mask & 0x20) !== 0) newMask |= 0x04; // 6 -> 3
                if ((mask & 0x40) !== 0) newMask |= 0x80; // 7 -> 8
                if ((mask & 0x80) !== 0) newMask |= 0x40; // 8 -> 7
                
                return String.fromCharCode(0x2800 + newMask);
            }

            // --- Lógica de UI Existente ---

            function actualizarContador() {
                const texto = document.getElementById('texto').value;
                document.getElementById('charCount').textContent = texto.length;
            }

            function actualizarPlaceholder() {
                const direccion = document.querySelector('input[name="direccion"]:checked').value;
                const textarea = document.getElementById('texto');
                const label = document.getElementById('labelTexto');

                if (direccion === 'ESPANOL_A_BRAILLE') {
                    textarea.placeholder = 'Escribe texto en español...';
                    label.textContent = 'Texto en español';
                } else {
                    textarea.placeholder = 'Pega texto en Braille Unicode...';
                    label.textContent = 'Texto en Braille';
                }
            }

            function obtenerUrlApi() {
                return '<%= request.getContextPath() %>/api/traducir';
            }

            function mostrarResultado(texto, tipo, emoji) {
                const resultado = document.getElementById('resultado');
                const resultadoEtiqueta = document.getElementById('resultadoEtiqueta');
                const resultText = document.getElementById('resultText');
                const resultEmoji = document.getElementById('resultEmoji');
                const resultDiv = document.getElementById('resultContainer');

                resultText.textContent = texto;
                resultEmoji.textContent = emoji || '';
                resultado.classList.remove('hidden');
                if (resultadoEtiqueta) {
                    resultadoEtiqueta.classList.remove('hidden');
                }

                resultDiv.className = 'p-6 rounded-xl border-2 ';
                if (tipo === 'success') {
                    resultDiv.className += 'bg-green-50 border-green-300';
                    resultText.className = 'text-2xl font-medium break-words select-all text-green-900';
                } else if (tipo === 'error') {
                    resultDiv.className += 'bg-red-50 border-red-300';
                    resultText.className = 'text-xl font-medium break-words select-all text-red-900';
                } else {
                    resultDiv.className += 'bg-gray-50 border-gray-300';
                    resultText.className = 'text-xl font-medium break-words select-all text-gray-900';
                }

                resultado.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
            }

            async function traducir() {
                const texto = document.getElementById('texto').value.trim();
                const direccion = document.querySelector('input[name="direccion"]:checked').value;
                const btnTraducir = document.getElementById('btnTraducir');
                const btnText = document.getElementById('btnText');
                const btnIcon = document.getElementById('btnIcon');

                ultimaTraduccionBraille = '';

                if (!texto) {
                    mostrarResultado('Por favor ingresa un texto para traducir', 'error', '⚠️');
                    return;
                }

                btnTraducir.disabled = true;
                btnTraducir.classList.add('opacity-75', 'cursor-not-allowed');
                btnIcon.innerHTML = '<circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" fill="none"></circle><path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>';
                btnIcon.classList.add('animate-spin');
                btnText.textContent = 'Traduciendo...';

                try {
                    const url = obtenerUrlApi();
                    const solicitud = {
                        texto: texto,
                        direccion: direccion
                    };

                    const response = await fetch(url, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json; charset=UTF-8'
                        },
                        body: JSON.stringify(solicitud)
                    });

                    const data = await response.json();

                    if (data.exito) {
                        mostrarResultado(data.textoTraducido, 'success', '');
                        
                        if (direccion.includes('A_BRAILLE')) {
                            ultimaTraduccionBraille = data.textoTraducido || '';
                        }
                        actualizarEstadoDescarga();
                    } else {
                        mostrarResultado(data.error || 'Error desconocido', 'error', '❌');
                        actualizarEstadoDescarga();
                    }

                } catch (error) {
                    console.error('Error:', error);
                    mostrarResultado('Error de conexión: ' + error.message, 'error', '❌');
                    actualizarEstadoDescarga();
                } finally {
                    btnTraducir.disabled = false;
                    btnTraducir.classList.remove('opacity-75', 'cursor-not-allowed');
                    btnIcon.classList.remove('animate-spin');
                    btnIcon.innerHTML = '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4"></path>';
                    btnText.textContent = 'Traducir';
                }
            }

            function limpiar() {
                document.getElementById('texto').value = '';
                document.getElementById('resultado').classList.add('hidden');
                if (document.getElementById('resultadoEtiqueta')) {
                    document.getElementById('resultadoEtiqueta').classList.add('hidden');
                }
                actualizarContador();
                document.getElementById('texto').focus();
                ultimaTraduccionBraille = '';
                actualizarEstadoDescarga();
            }

            async function copiar() {
                const resultTextEl = document.getElementById('resultText');
                const btnCopiar = document.getElementById('btnCopiar');
                const btnText = document.getElementById('btnTextCopiar');
                const texto = resultTextEl ? resultTextEl.textContent.trim() : '';

                if (!texto) {
                    mostrarResultado('No hay texto para copiar', 'error', '⚠️');
                    return;
                }

                const prevText = btnText ? btnText.textContent : '';

                try {
                    if (btnCopiar) btnCopiar.disabled = true;
                    if (btnText) btnText.textContent = 'Copiando...';

                    if (navigator.clipboard && navigator.clipboard.writeText) {
                        await navigator.clipboard.writeText(texto);
                    } else {
                        const textarea = document.createElement('textarea');
                        textarea.value = texto;
                        document.body.appendChild(textarea);
                        textarea.select();
                        document.execCommand('copy');
                        document.body.removeChild(textarea);
                    }

                    if (btnText) btnText.textContent = 'Copiado';
                    setTimeout(() => {
                        if (btnText) btnText.textContent = prevText;
                        if (btnCopiar) btnCopiar.disabled = false;
                    }, 1800);
                } catch (err) {
                    console.error('Error copiando:', err);
                    if (btnCopiar) btnCopiar.disabled = false;
                    if (btnText) btnText.textContent = 'Copiar';
                }
            }

            function actualizarEstadoDescarga() {
                const btnDescargar = document.getElementById('btnDescargar');
                if (!btnDescargar) return;
                const habilitado = !!ultimaTraduccionBraille;
                btnDescargar.disabled = !habilitado;
                btnDescargar.classList.toggle('opacity-50', !habilitado);
                btnDescargar.classList.toggle('cursor-not-allowed', !habilitado);
            }

            function setDescargaEnProceso(enProceso) {
                const btn = document.getElementById('btnDescargar');
                if (!btn) return;
                btn.disabled = enProceso || !ultimaTraduccionBraille;
                btn.classList.toggle('opacity-75', enProceso);
                if (enProceso) {
                    btn.innerHTML = '<span class="font-semibold">Generando PNG...</span>';
                } else {
                    btn.innerHTML = '<svg id="btnIconDescargar" class="w-6 h-6 mb-1" fill="none" stroke="currentColor" viewBox="0 0 24 24"><path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v2a2 2 0 002 2h12a2 2 0 002-2v-2M7 10l5 5 5-5M12 15V3"></path></svg><span id="btnTextDescargar" class="font-semibold text-sm">Descargar PNG</span>';
                }
            }

            async function descargarBraillePng() {
                let textoParaExportar = (ultimaTraduccionBraille || '').trim();
                const modoEspejo = document.getElementById('checkEspejo').checked;

                if (!textoParaExportar) {
                    mostrarResultado('No hay traducción Braille para descargar', 'error', '⚠️');
                    return;
                }

                if (modoEspejo) {
                    textoParaExportar = espejarBrailleJs(textoParaExportar);
                }

                if (!window.html2canvas) {
                    mostrarResultado('Biblioteca de exportación no disponible', 'error', '❌');
                    return;
                }

                setDescargaEnProceso(true);

                try {
                    const exporter = document.createElement('div');
                    exporter.id = 'braille-exporter';
                    Object.assign(exporter.style, {
                        position: 'fixed', left: '-99999px', top: '0',
                        width: '2480px', background: '#ffffff', color: '#000000',
                        padding: '80px', boxSizing: 'border-box', textAlign: 'center',
                        whiteSpace: 'pre-wrap', wordWrap: 'break-word', overflowWrap: 'break-word',
                        fontFamily: "'Segoe UI Symbol','Arial Unicode MS',system-ui,sans-serif",
                        lineHeight: '1.12', letterSpacing: '8px'
                    });

                    const len = textoParaExportar.length;
                    let fontSize = 220;
                    if (len > 40) fontSize = 180;
                    if (len > 80) fontSize = 140;
                    if (len > 140) fontSize = 110;
                    exporter.style.fontSize = fontSize + 'px';
                    exporter.textContent = textoParaExportar;
                    document.body.appendChild(exporter);

                    const scale = Math.max(2, Math.ceil(window.devicePixelRatio || 1));
                    const canvas = await html2canvas(exporter, { backgroundColor: '#ffffff', scale: scale });

                    const enlace = document.createElement('a');
                    enlace.href = canvas.toDataURL('image/png');
                    enlace.download = modoEspejo ? 'braille-espejo-escritura.png' : 'braille-lectura-normal.png';
                    enlace.click();

                    document.body.removeChild(exporter);
                } catch (error) {
                    console.error('Error generando PNG:', error);
                    mostrarResultado('No se pudo generar el PNG: ' + error.message, 'error', '❌');
                } finally {
                    setDescargaEnProceso(false);
                }
            }
        </script>
    </body>

    </html>

