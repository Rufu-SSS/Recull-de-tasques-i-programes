using UnityEngine;

// CONCEPTE 1: Enumeraci�
// Definim els possibles estats d'una nau i els tipus d'arma
// com a tipus propis, no com a n�meros m�gics (1, 2, 3...)
public enum EstatNau
{
    Volant,
    Dispara,
    Danyada,
    Destruida
}
public enum TipusArma
{
    Laser,
    Missile,
    Plasma
}