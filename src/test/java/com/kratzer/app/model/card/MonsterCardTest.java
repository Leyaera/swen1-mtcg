package com.kratzer.app.model.card;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import static org.junit.jupiter.api.Assertions.*;

class MonsterCardTest {
    @Mock
    CardInterface cardMock;

    @Test
    @DisplayName("MonsterCard should be MONSTER")
    void testGetCardTypeMonster() {
        // arrange
        Card monsterCard = new MonsterCard("", "MonsterCard", 0, ElementType.NORMAL);

        // act
        CardType cardType = monsterCard.getCardType();

        // assert
        assertNotNull(cardType);
        assertEquals(CardType.MONSTER, cardType);
    }

    @Test
    @DisplayName("SpellCard should be SPELL")
    void testGetCardTypeSpell() {
        // arrange
        Card spellCard = new SpellCard("", "SpellCard", 0, ElementType.NORMAL);

        // act
        CardType cardType = spellCard.getCardType();

        // assert
        assertNotNull(cardType);
        assertEquals(CardType.SPELL, cardType);
    }

    @Test
    @DisplayName("The armor of Knights is so heavy that WaterSpells make them drown them instantly")
    void testWinsAgainstWaterSpellKnight() {
        // arrange
        Card waterSpell = new SpellCard("", "WaterSpell", 0, ElementType.WATER);
        Card knight = new MonsterCard("", "Knight", 0, ElementType.NORMAL);

        // act
        boolean win = waterSpell.instantWinsAgainst(knight);

        // assert
        assertTrue(win);
    }

    @Test
    @DisplayName("Goblins are too afraid of Dragons to attack")
    void testWinsAgainstDragonGoblin (){
        // arrange
        Card dragon = new MonsterCard("", "Dragon", 0, ElementType.NORMAL);
        Card goblin = new MonsterCard("", "Goblin", 0, ElementType.NORMAL);

        // act
        boolean win = dragon.instantWinsAgainst(goblin);

        // assert
        assertTrue(win);
    }

    @Test
    @DisplayName("Wizzard can control Orks so they are not able to damage them")
    void testWinsAgainstWizzardOrk (){
        // arrange
        Card wizzard = new MonsterCard("", "Wizzard", 0, ElementType.FIRE);
        Card ork = new MonsterCard("", "Ork", 0, ElementType.NORMAL);

        // act
        boolean win = wizzard.instantWinsAgainst(ork);

        // assert
        assertTrue(win);
    }

    @Test
    @DisplayName("The Kraken is immune against spells")
    void testWinsAgainstKrakenSpell (){
        // arrange
        Card kraken = new MonsterCard("", "Kraken", 0, ElementType.WATER);
        Card spellCard = new SpellCard("", "SpellCard", 0, ElementType.NORMAL);

        // act
        boolean win = kraken.instantWinsAgainst(spellCard);

        // assert
        assertTrue(win);
    }

    @Test
    @DisplayName("The FireElves know Dragons since they were little and can evade their attacks")
    void testWinsAgainstFireElfDragon (){
        // arrange
        Card fireElf = new MonsterCard("", "Elf", 0, ElementType.FIRE);
        Card dragon = new MonsterCard("", "Dragon", 0, ElementType.FIRE);

        // act
        boolean win = fireElf.instantWinsAgainst(dragon);

        // assert
        assertTrue(win);
    }

    @Test
    @DisplayName("Element Type does not effect pure monster fights")
    void testDamageAgainst() {
        // arrange
        Card monsterCard1 = new MonsterCard ("", "MonsterCard1", 10, ElementType.WATER);
        Card monsterCard2 = new MonsterCard ("", "MonsterCard2", 20, ElementType.FIRE);

        // act
        float monster1Damage = monsterCard1.damageAgainst(monsterCard2);

        // assert
        assertEquals(10, monster1Damage);
    }
}