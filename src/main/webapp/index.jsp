<%@ page contentType="text/html;charset=UTF-8" language="java" %>
    <!DOCTYPE html>
    <html lang="es">

    <head>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <title>Traductor Braille - Sistema de Traducción Bidireccional</title>
        <script src="https://cdn.tailwindcss.com"></script>
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
                            <p class="text-sm text-gray-500">Sistema de traducción bidireccional</p>
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

            <!-- Features Cards -->
            <div class="grid md:grid-cols-3 gap-6 mb-12 animate-slide-up">
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
                    <p class="text-gray-600">Letras A-Z, Ñ y vocales acentuadas (á, é, í, ó, ú) totalmente soportadas
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

                <!-- Feature 3 -->
                <div
                    class="bg-white rounded-2xl p-6 shadow-lg hover:shadow-xl transition-all duration-300 border border-gray-100 hover:-translate-y-1">
                    <div
                        class="w-14 h-14 bg-gradient-to-br from-green-100 to-green-200 rounded-xl flex items-center justify-center mb-4">
                        <svg class="w-8 h-8 text-green-600" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M13 10V3L4 14h7v7l9-11h-7z"></path>
                        </svg>
                    </div>
                    <h3 class="text-xl font-bold text-gray-800 mb-2">Tiempo Real</h3>
                    <p class="text-gray-600">Traducción bidireccional instantánea con validación inteligente</p>
                </div>
            </div>

            <!-- Translator Section -->
            <div class="bg-white rounded-3xl shadow-2xl p-8 md:p-12 max-w-5xl mx-auto animate-scale-in">

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

                <!-- Form -->
                <form id="formTraduccion" class="space-y-6">

                    <!-- Direction Selector -->
                    <div>
                        <label class="block text-sm font-semibold text-gray-700 mb-3">
                            Dirección de traducción
                        </label>
                        <div class="grid grid-cols-3 gap-4">
                            <label class="relative cursor-pointer col-span-2">
                                <input type="radio" name="direccion" value="ESPANOL_A_BRAILLE" checked class="peer sr-only">
                                <div class="border-2 border-gray-200 peer-checked:border-primary-500 peer-checked:bg-primary-50 rounded-xl p-4 transition-all duration-200 hover:border-primary-300">
                                    <div class="flex items-center justify-between mb-2">
                                        <span class="text-2xl">🔤</span>
                                        <svg class="w-5 h-5 text-primary-600 opacity-0 peer-checked:opacity-100" fill="currentColor" viewBox="0 0 20 20">
                                            <path fill-rule="evenodd" d="M10 18a8 8 0 100-16 8 8 0 000 16zm3.707-9.293a1 1 0 00-1.414-1.414L9 10.586 7.707 9.293a1 1 0 00-1.414 1.414l2 2a1 1 0 001.414 0l4-4z" clip-rule="evenodd"></path>
                                        </svg>
                                    </div>
                                    <p class="font-semibold text-gray-800">Español → Braille</p>
                                    <p class="text-xs text-gray-500 mt-1">Convierte texto a Braille Unicode</p>
                                </div>
                            </label>

                            <button type="button" class="col-span-1 border-2 border-gray-200 rounded-xl p-4 flex flex-col items-center justify-center text-primary-600 hover:border-primary-300 transition-all duration-200">
                                <svg class="w-8 h-8 mb-2" fill="none" stroke="currentColor" viewBox="0 0 24 24">
                                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M4 16v2a2 2 0 002 2h12a2 2 0 002-2v-2M7 10l5 5 5-5M12 15V3"></path>
                                </svg>
                                <span class="font-semibold">Descargar</span>
                            </button>
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
                </form>

                <!-- Result Section -->
                <div id="resultado" class="mt-8 hidden">
                    <div class="p-6 rounded-xl border-2">
                        <div class="flex items-start space-x-3">
                            <span id="resultEmoji" class="text-3xl"></span>
                            <div class="flex-1">
                                <p class="text-sm font-semibold text-gray-600 mb-2">Resultado:</p>
                                <p id="resultText" class="text-xl font-medium break-words select-all"></p>
                            </div>
                        </div>
                    </div>
                </div>

                <!--BOTON COPIAR-->
                <div class="flex gap-4 mt-8">
                    <button type="button" onclick="copiar()" id="btnCopiar"
                            class="flex-1 bg-gradient-to-r from-primary-500 to-accent-500 hover:from-primary-600 hover:to-accent-600 text-white font-semibold py-4 px-6 rounded-xl shadow-lg hover:shadow-xl transition-all duration-200 transform hover:-translate-y-0.5 flex items-center justify-center space-x-2">
                        <span id="btnTextCopiar">Copiar</span>
                    </button>
                </div>

            </div>


            <!-- Additional Info Section -->
            <div class="max-w-5xl mx-auto mt-12 grid md:grid-cols-2 gap-6">
                <div class="bg-white rounded-xl p-6 shadow-lg border border-gray-100">
                    <h3 class="text-lg font-bold text-gray-800 mb-3 flex items-center">
                        <svg class="w-6 h-6 text-primary-500 mr-2" fill="none" stroke="currentColor"
                            viewBox="0 0 24 24">
                            <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2"
                                d="M9.663 17h4.673M12 3v1m6.364 1.636l-.707.707M21 12h-1M4 12H3m3.343-5.657l-.707-.707m2.828 9.9a5 5 0 117.072 0l-.548.547A3.374 3.374 0 0014 18.469V19a2 2 0 11-4 0v-.531c0-.895-.356-1.754-.988-2.386l-.548-.547z">
                            </path>
                        </svg>
                        ¿Cómo usar?
                    </h3>
                    <ol class="space-y-2 text-sm text-gray-600 list-decimal list-inside">
                        <li>Selecciona la dirección de traducción</li>
                        <li>Escribe o pega tu texto</li>
                        <li>Haz clic en "Traducir"</li>
                        <li>Copia el resultado seleccionándolo</li>
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
        </main>

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
            // Inicialización
            document.addEventListener('DOMContentLoaded', function () {
                actualizarContador();
                actualizarPlaceholder();

                // Event listeners
                document.getElementById('texto').addEventListener('input', actualizarContador);
                document.querySelectorAll('input[name="direccion"]').forEach(radio => {
                    radio.addEventListener('change', actualizarPlaceholder);
                });

                // Atajo de teclado Ctrl+Enter
                document.getElementById('texto').addEventListener('keydown', function (e) {
                    if (e.ctrlKey && e.key === 'Enter') {
                        e.preventDefault();
                        traducir();
                    }
                });
            });

            // Actualizar contador de caracteres
            function actualizarContador() {
                const texto = document.getElementById('texto').value;
                document.getElementById('charCount').textContent = texto.length;
            }

            // Actualizar placeholder y label según dirección
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

            // Obtener URL de la API
            function obtenerUrlApi() {
                return '<%= request.getContextPath() %>/api/traducir';
            }

            // Mostrar resultado
            function mostrarResultado(texto, tipo, emoji) {
                const resultado = document.getElementById('resultado');
                const resultText = document.getElementById('resultText');
                const resultEmoji = document.getElementById('resultEmoji');
                const resultDiv = resultado.querySelector('div');

                resultText.textContent = texto;
                resultEmoji.textContent = emoji;
                resultado.classList.remove('hidden');

                // Aplicar estilos según tipo
                resultDiv.className = 'p-6 rounded-xl border-2 ';
                if (tipo === 'success') {
                    resultDiv.className += 'bg-green-50 border-green-300';
                    resultText.className = 'text-xl font-medium break-words select-all text-green-900';
                } else if (tipo === 'error') {
                    resultDiv.className += 'bg-red-50 border-red-300';
                    resultText.className = 'text-xl font-medium break-words select-all text-red-900';
                } else {
                    resultDiv.className += 'bg-gray-50 border-gray-300';
                    resultText.className = 'text-xl font-medium break-words select-all text-gray-900';
                }

                // Scroll suave al resultado
                resultado.scrollIntoView({ behavior: 'smooth', block: 'nearest' });
            }

            // Función principal de traducción
            async function traducir() {
                const texto = document.getElementById('texto').value.trim();
                const direccion = document.querySelector('input[name="direccion"]:checked').value;
                const btnTraducir = document.getElementById('btnTraducir');
                const btnText = document.getElementById('btnText');
                const btnIcon = document.getElementById('btnIcon');

                // Validación
                if (!texto) {
                    mostrarResultado('Por favor ingresa un texto para traducir', 'error', '⚠️');
                    return;
                }

                // Deshabilitar botón y mostrar loading
                btnTraducir.disabled = true;
                btnTraducir.classList.add('opacity-75', 'cursor-not-allowed');
                btnIcon.innerHTML = '<animateTransform attributeName="transform" type="rotate" from="0 12 12" to="360 12 12" dur="1s" repeatCount="indefinite"/>';
                btnIcon.innerHTML = '';
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
                    } else {
                        mostrarResultado(data.error || 'Error desconocido', 'error', '❌');
                    }

                } catch (error) {
                    console.error('Error:', error);
                    mostrarResultado('Error de conexión: ' + error.message, 'error', '❌');
                } finally {
                    // Rehabilitar botón
                    btnTraducir.disabled = false;
                    btnTraducir.classList.remove('opacity-75', 'cursor-not-allowed');
                    btnIcon.classList.remove('animate-spin');
                    btnIcon.innerHTML = '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4"></path>';
                    btnText.textContent = 'Traducir';
                }
            }

            // Limpiar formulario
            function limpiar() {
                document.getElementById('texto').value = '';
                document.getElementById('resultado').classList.add('hidden');
                actualizarContador();
                document.getElementById('texto').focus();
            }

            // Copiar resultado al portapapeles
            async function copiar() {
                const resultTextEl = document.getElementById('resultText');
                const btnCopiar = document.getElementById('btnCopiar');
                const btnIcon = document.getElementById('btnIconCopiar');
                const btnText = document.getElementById('btnTextCopiar');

                const texto = resultTextEl ? resultTextEl.textContent.trim() : '';

                if (!texto) {
                    // Mostrar mensaje de error si no hay nada que copiar
                    mostrarResultado('No hay texto para copiar', 'error', '⚠️');
                    return;
                }

                // Guardar estado anterior para restaurar después
                const prevIcon = btnIcon ? btnIcon.innerHTML : '';
                const prevText = btnText ? btnText.textContent : '';

                try {
                    if (btnCopiar) {
                        btnCopiar.disabled = true;
                    }

                    if (btnIcon) {
                        // Mostrar spinner pequeño
                        btnIcon.innerHTML = '<circle class="opacity-25" cx="12" cy="12" r="10" stroke="currentColor" stroke-width="4" fill="none"></circle><path class="opacity-75" fill="currentColor" d="M4 12a8 8 0 018-8V0C5.373 0 0 5.373 0 12h4zm2 5.291A7.962 7.962 0 014 12H0c0 3.042 1.135 5.824 3 7.938l3-2.647z"></path>';
                        btnIcon.classList.add('animate-spin');
                    }
                    if (btnText) btnText.textContent = 'Copiando...';

                    // Intentar usar Clipboard API
                    if (navigator.clipboard && navigator.clipboard.writeText) {
                        await navigator.clipboard.writeText(texto);
                    } else {
                        // Fallback para navegadores antiguos
                        const textarea = document.createElement('textarea');
                        textarea.value = texto;
                        textarea.setAttribute('readonly', '');
                        textarea.style.position = 'absolute';
                        textarea.style.left = '-9999px';
                        document.body.appendChild(textarea);
                        textarea.select();
                        try {
                            document.execCommand('copy');
                        } finally {
                            document.body.removeChild(textarea);
                        }
                    }

                    // Feedback de éxito
                    if (btnIcon) {
                        btnIcon.classList.remove('animate-spin');
                        btnIcon.innerHTML = '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M5 13l4 4L19 7"></path>';
                    }
                    if (btnText) btnText.textContent = 'Copiado';

                    // Restaurar después de un momento
                    setTimeout(() => {
                        if (btnIcon) btnIcon.innerHTML = prevIcon;
                        if (btnText) btnText.textContent = prevText;
                        if (btnCopiar) btnCopiar.disabled = false;
                    }, 1800);

                } catch (err) {
                    console.error('Error copiando al portapapeles:', err);
                    mostrarResultado('Error al copiar: ' + (err && err.message ? err.message : err), 'error', '❌');

                    // Restaurar estado en caso de error
                    if (btnCopiar) btnCopiar.disabled = false;
                    if (btnIcon) {
                        btnIcon.classList.remove('animate-spin');
                        btnIcon.innerHTML = '<path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M8 7h12m0 0l-4-4m4 4l-4 4m0 6H4m0 0l4 4m-4-4l4-4"></path>';
                    }
                    if (btnText) btnText.textContent = 'Copiar';
                }
            }
        </script>
    </body>

    </html>