package com.kratzer.app.model.card;

import com.fasterxml.jackson.annotation.JsonAlias;
import lombok.Getter;
import java.util.Random;

public class Card implements com.kratzer.app.model.card.CardInterface {
    @Getter
    @JsonAlias({"Id"})
    String id;

    @Getter
    @JsonAlias({"Name"})
    String name;

    @Getter
    @JsonAlias({"Damage"})
    float damage;

    @Getter
    CardType cardType;

    @Getter
    ElementType elementType;

    public Card () {

    }

    public Card (String id, String name, float damage, ElementType elementType) {
        this.id = id;
        this.name = name;
        this.damage = damage;
        this.elementType = elementType;
    }

    @Override
    public boolean isImmune() {
        int min = 0;
        int max = 100;

        Random random = new Random();
        int lucky = random.nextInt(max + min) + min;
        if (lucky > 97) {
            return true;
        }
        return false;
    }

    @Override
    public float damageAgainst(CardInterface opponentCard) {
        // Effectiveness of spell cards
        if (CardType.SPELL.equals(this.getCardType())) {
            // Effective: damage is doubled
            if ((ElementType.WATER.equals(this.getElementType()) && ElementType.FIRE.equals(opponentCard.getElementType())) ||
                    (ElementType.FIRE.equals(this.getElementType()) && ElementType.NORMAL.equals(opponentCard.getElementType())) ||
                    (ElementType.NORMAL.equals(this.getElementType()) && ElementType.WATER.equals(opponentCard.getElementType()))) {
                System.out.println(this.getDamage() * 2);
                return this.getDamage() * 2;
            }

            // Not effective: damage is halved
            if ((ElementType.FIRE.equals(this.getElementType()) && ElementType.WATER.equals(opponentCard.getElementType())) ||
                    (ElementType.NORMAL.equals(this.getElementType()) && ElementType.FIRE.equals(opponentCard.getElementType())) ||
                    (ElementType.WATER.equals(this.getElementType()) && ElementType.NORMAL.equals(opponentCard.getElementType()))) {
                System.out.println(this.getDamage() / 2);
                return this.getDamage() / 2;
            }
        }

        // No effect: no change of damage, direct comparison
        System.out.println(this.getDamage());
        return this.getDamage();
    }

    @Override
    public boolean instantWinsAgainst(CardInterface opponentCard) {
        // SPECIAL FEATURE
        /*if (isImmune()) {
            return true;
        }*/
        // MONSTER vs MONSTER
        if (CardType.MONSTER.equals(this.getCardType()) && CardType.MONSTER.equals(opponentCard.getCardType())) {
            // Goblins are too afraid of Dragons to attack
            if ("Dragon".equals(this.getName()) && "Goblin".equals(opponentCard.getName())) {
                return true;
            }

            // Wizard can control Orcs, so they are not able to damage them
            if ("Wizard".equals(this.getName()) && "Ork".equals(opponentCard.getName())) {
                return true;
            }

            // FireElves know Dragons since they were little and can evade their attacks
            if ("Elf".equals(this.getName()) && ElementType.FIRE.equals(this.getElementType()) && "Dragon".equals(opponentCard.getName())) {
                return true;
            }
        }

        // MONSTER vs SPELL
        if (CardType.SPELL.equals(this.getCardType()) && CardType.MONSTER.equals(opponentCard.getCardType())) {
            // Armor of Knights are so heavy that WaterSpells make them drown instantly
            if (ElementType.WATER.equals(this.getElementType()) && "Knight".equals(opponentCard.getName())) {
                return true;
            }
        }

        // SPELL vs MONSTER
        if (CardType.MONSTER.equals(this.getCardType()) && CardType.SPELL.equals(opponentCard.getCardType())) {
            // Kraken is immune against spells
            if ("Kraken".equals(this.getName())) {
                return true;
            }
        }

        return false;
    }
}
