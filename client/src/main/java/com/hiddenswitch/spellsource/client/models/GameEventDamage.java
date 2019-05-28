/*
 * Hidden Switch Spellsource API
 * The Spellsource API for matchmaking, user accounts, collections management and more.  To get started, create a user account and make sure to include the entirety of the returned login token as the X-Auth-Token header. You can reuse this token, or login for a new one.  ClientToServerMessage and ServerToClientMessage are used for the realtime game state and actions two-way websocket interface for actually playing a game. Envelope is used for the realtime API services.  For the routes that correspond to the paths in this file, visit the Gateway.java file in the Spellsource-Server GitHub repository located in this definition file. 
 *
 * OpenAPI spec version: 3.0.4
 * Contact: ben@hiddenswitch.com
 *
 * NOTE: This class is auto generated by the swagger code generator program.
 * https://github.com/swagger-api/swagger-codegen.git
 * Do not edit the class manually.
 */


package com.hiddenswitch.spellsource.client.models;

import java.util.Objects;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import com.hiddenswitch.spellsource.client.models.DamageTypeEnum;
import com.hiddenswitch.spellsource.client.models.Entity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.io.Serializable;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * GameEventDamage
 */
@JsonInclude(JsonInclude.Include.NON_DEFAULT)

public class GameEventDamage implements Serializable {
  private static final long serialVersionUID = 1L;

  @JsonProperty("victim")
  private Entity victim = null;

  @JsonProperty("damage")
  private Integer damage = null;

  @JsonProperty("source")
  private Entity source = null;

  @JsonProperty("damageType")
  private DamageTypeEnum damageType = null;

  public GameEventDamage victim(Entity victim) {
    this.victim = victim;
    return this;
  }

   /**
   * Get victim
   * @return victim
  **/
  @ApiModelProperty(value = "")
  public Entity getVictim() {
    return victim;
  }

  public void setVictim(Entity victim) {
    this.victim = victim;
  }

  public GameEventDamage damage(Integer damage) {
    this.damage = damage;
    return this;
  }

   /**
   * Get damage
   * @return damage
  **/
  @ApiModelProperty(value = "")
  public Integer getDamage() {
    return damage;
  }

  public void setDamage(Integer damage) {
    this.damage = damage;
  }

  public GameEventDamage source(Entity source) {
    this.source = source;
    return this;
  }

   /**
   * Get source
   * @return source
  **/
  @ApiModelProperty(value = "")
  public Entity getSource() {
    return source;
  }

  public void setSource(Entity source) {
    this.source = source;
  }

  public GameEventDamage damageType(DamageTypeEnum damageType) {
    this.damageType = damageType;
    return this;
  }

   /**
   * Get damageType
   * @return damageType
  **/
  @ApiModelProperty(value = "")
  public DamageTypeEnum getDamageType() {
    return damageType;
  }

  public void setDamageType(DamageTypeEnum damageType) {
    this.damageType = damageType;
  }


  @Override
  public boolean equals(java.lang.Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    GameEventDamage gameEventDamage = (GameEventDamage) o;
    return Objects.equals(this.victim, gameEventDamage.victim) &&
        Objects.equals(this.damage, gameEventDamage.damage) &&
        Objects.equals(this.source, gameEventDamage.source) &&
        Objects.equals(this.damageType, gameEventDamage.damageType);
  }

  @Override
  public int hashCode() {
    return Objects.hash(victim, damage, source, damageType);
  }


  @Override
  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("class GameEventDamage {\n");
    
    sb.append("    victim: ").append(toIndentedString(victim)).append("\n");
    sb.append("    damage: ").append(toIndentedString(damage)).append("\n");
    sb.append("    source: ").append(toIndentedString(source)).append("\n");
    sb.append("    damageType: ").append(toIndentedString(damageType)).append("\n");
    sb.append("}");
    return sb.toString();
  }

  /**
   * Convert the given object to string with each line indented by 4 spaces
   * (except the first line).
   */
  private String toIndentedString(java.lang.Object o) {
    if (o == null) {
      return "null";
    }
    return o.toString().replace("\n", "\n    ");
  }

}

