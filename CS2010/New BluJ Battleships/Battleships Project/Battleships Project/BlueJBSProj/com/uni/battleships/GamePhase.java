package com.uni.battleships;

/**
 * User: dogmaan
 * Date: 01/07/12
 * Time: 01:27
 */

/**
 * this enumerator is used to establish what phase the
 * current game state is in
 */
public enum GamePhase
{
    INITIAL, SELECTION_PLAYER1, SELECTION_PLAYER2, SHOTTURN_PLAYER1, SHOTTURN_PLAYER2, GAMEEND
}
