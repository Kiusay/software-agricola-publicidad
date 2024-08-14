// JavaScript personalizado para SAP - Software Agrícola de Publicidad

document.addEventListener("DOMContentLoaded", function() {
    // Función para ocultar la barra de derechos reservados
    function ocultarBarraDerechos() {
        var botonOcultar = document.querySelector(".button-10");
        var barraDerechos = document.querySelector(".section-3");

        if (botonOcultar && barraDerechos) {
            botonOcultar.addEventListener("click", function(event) {
                event.preventDefault();
                barraDerechos.style.display = "none";
            });
        }
    }

    // Función para cargar las fuentes web
    function cargarFuentesWeb() {
        WebFont.load({
            google: {
                families: ["Changa One:400,400italic", "Ubuntu:300,300italic,400,400italic,500,500italic,700,700italic"]
            }
        });
    }

    // Inicializar todas las funciones
    function iniciar() {
        ocultarBarraDerechos();
        cargarFuentesWeb();
    }

    // Ejecutar todas las funciones cuando el documento esté completamente cargado
    iniciar();
});
