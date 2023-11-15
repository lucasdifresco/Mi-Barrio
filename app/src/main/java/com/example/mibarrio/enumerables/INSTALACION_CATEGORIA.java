package com.example.mibarrio.enumerables;

public enum INSTALACION_CATEGORIA {
    OTROS(0),
    CALLES(1),
    PLAZAS(2),
    OFICINAS_PUBLICAS(3),
    ESCUELAS_PRIMARIAS(4),
    ESCUELAS_SECUNDARIAS(5),
    COMEDORES_ESCOLARES(6),
    MUSEOS(7),
    CAMPING_MUNICIPALES(8);


    private int valor;
    INSTALACION_CATEGORIA(int valor) { this.valor = valor; }
    public int toInt(){ return valor; }
    public static String getValueById(int id){
        switch(id){
            case 0: return INSTALACION_CATEGORIA.OTROS.toString();
            case 1: return INSTALACION_CATEGORIA.CALLES.toString();
            case 2: return INSTALACION_CATEGORIA.PLAZAS.toString();
            case 3: return INSTALACION_CATEGORIA.OFICINAS_PUBLICAS.toString();
            case 4: return INSTALACION_CATEGORIA.ESCUELAS_PRIMARIAS.toString();
            case 5: return INSTALACION_CATEGORIA.ESCUELAS_SECUNDARIAS.toString();
            case 6: return INSTALACION_CATEGORIA.COMEDORES_ESCOLARES.toString();
            case 7: return INSTALACION_CATEGORIA.MUSEOS.toString();
            case 8: return INSTALACION_CATEGORIA.CAMPING_MUNICIPALES.toString();
            default: return "Invalido";
        }
    }
}
