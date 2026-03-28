# Create: New Foods

![banner](docs/assets/create_new_foods_banner.png)


A Fabric mod for Minecraft that extends [**Create Fly**](https://modrinth.com/mod/create-fly) and 
adds new fluids, crops, food items, and Create-compatible recipes. It also complements [Farmer's Delight Refabricated](https://modrinth.com/mod/farmers-delight-refabricated) and other 
food mods.

**Dependencies:** 
 - [**Create Fly**](https://modrinth.com/mod/create-fly)
 - [**Fabric API**](https://modrinth.com/mod/fabric-api)

**Optional dependencies:**
 - [Farmer's Delight Refabricated](https://modrinth.com/mod/farmers-delight-refabricated)

---

## Contents

- [Crops](#crops)
- [Fluids](#fluids)
- [Food Items](#food-items)
- [Recipes](#recipes)
  - [Crafting Table](#crafting-table)
  - [Create Mixing](#create-mixing)
  - [Create Filling](#create-filling)
  - [Farmer's Delight Mixing](#farmers-delight-mixing)

---

## Crops

### Vanilla (`create_new_food:vanilla_bean`)

A slow-growing orchid that produces vanilla, inspired by real-life vanilla plants.

- **Planted with:** Vanilla item (right-click on valid ground)
- **Grows on:** Grass, dirt, podzol, farmland, mud, moss and similar blocks
- **Light requirement:** Minimum level 7
- **Growth stages:** 4 (0–3)
  - Stages 0–2: single-block plant
  - Stage 3: two-block-tall plant (top block appears automatically when space is free above)
- **Growth speed:** Slow — roughly half the speed of sweet berries
- **Harvest:** Right-click at stage 3 to collect 1–2 vanilla without destroying the plant. The plant resets to stage 1.
- **Breaking:** Breaking at stage 3 also drops vanilla. Breaking at earlier stages drops nothing.
- **Bone meal:** Supported — advances one stage per use, places the top block when reaching stage 3.
- **World generation:** Spawns naturally at stage 2 in Jungle, Bamboo Jungle, Sparse Jungle, Mangrove Swamp, and Flower Forest biomes.

---

## Fluids

All fluids are flowable, can be stored in buckets, and are compatible with Create Fly tanks and mixing bowls.

| Fluid            | Bucket Item             | Fog Color |
|------------------|-------------------------|-----------|
| Pumpkin Pulp     | Pumpkin Pulp Bucket     | Orange    |
| Sweet Berry Pulp | Sweet Berry Pulp Bucket | Dark Red  |
| Vanilla          | Vanilla Bucket          | Cream     |
| Melon Pulp       | Melon Pulp Bucket       | Red       |
| Apple Pulp       | Apple Pulp Bucket       | Gold      |
| Glow Berry Pulp  | Glow Berry Pulp Bucket  | Green     |

Each fluid has a custom tint applied when the player is submerged in it.

---

## Food Items

### Ice Cream Cone (`create_new_food:icecream_cone`)

The base item for all ice cream variants. Crafted or mixed.

### Ice Cream Variants

All variants grant nutrition 3, saturation 0.4, and are always edible.

| Item                                                      | Effect (12s)       |
|-----------------------------------------------------------|--------------------|
| Ice Cream (`create_new_food:icecream`)                        | Fire Resistance II |
| Chocolate Ice Cream (`create_new_food:icecream_chocolate`)    | Health Boost II    |
| Pumpkin Ice Cream (`create_new_food:icecream_pumpkin`)        | Speed II           |
| Sweet Berry Ice Cream (`create_new_food:icecream_sweetberry`) | Regeneration II    |
| Vanilla Ice Cream (`create_new_food:icecream_vanilla`)        | Fire Resistance II |
| Melon Ice Cream (`create_new_food:icecream_melon`)            | Absorption II      |
| Apple Ice Cream (`create_new_food:icecream_apple`)            | Strength II        |
| Glow Berry Ice Cream (`create_new_food:icecream_glow_berry`)  | Glowing II         |

---

### Cupcake (`create_new_food:cupcake_base`)

The base item for all cupcake variants. Crafted via table or Create mixing (heated). Edible on its own (nutrition 2, saturation 0.1).

### Cupcake Variants

All variants grant nutrition 5, saturation 0.6, and are always edible.

| Item                                                   | Effect (12s)       |
|--------------------------------------------------------|--------------------|
| Chocolate Cupcake (`create_new_food:cupcake_chocolate`)       | Health Boost II    |
| Pumpkin Cupcake (`create_new_food:cupcake_pumpkin`)           | Speed II           |
| Sweet Berry Cupcake (`create_new_food:cupcake_sweetberry`)    | Regeneration II    |
| Vanilla Cupcake (`create_new_food:cupcake_vanilla`)           | Fire Resistance II |
| Melon Cupcake (`create_new_food:cupcake_melon`)               | Absorption II      |
| Apple Cupcake (`create_new_food:cupcake_apple`)               | Strength II        |
| Glow Berry Cupcake (`create_new_food:cupcake_glow_berry`)     | Night Vision II    |

---

### Yogurt (`create_new_foods:yogurt`)

A dairy item crafted with milk, a glass bottle, bone meal, and sugar. Edible on its own (nutrition 4, saturation 0.5), granting Regeneration I for 8s.

### Yogurt Variants

All variants grant nutrition 5, saturation 0.6, and are always edible.

| Item                                                        | Effect (12s)       |
|-------------------------------------------------------------|--------------------|
| Pumpkin Yogurt (`create_new_foods:yogurt_pumpkin`)          | Speed II           |
| Sweet Berry Yogurt (`create_new_foods:yogurt_sweetberry`)   | Regeneration II    |
| Vanilla Yogurt (`create_new_foods:yogurt_vanilla`)          | Fire Resistance II |
| Melon Yogurt (`create_new_foods:yogurt_melon`)              | Absorption II      |
| Apple Yogurt (`create_new_foods:yogurt_apple`)              | Strength II        |
| Glow Berry Yogurt (`create_new_foods:yogurt_glow_berry`)    | Glowing II         |

---

## Recipes

### Crafting Table

#### Ice Cream Cone (4x)
```
 M
WXW
 W
```
- `W` = Wheat (or wheat-like item, like Wheat_flour)
- `X` = Sugar
- `M` = Milk bucket

#### Ice Cream
```
 O
 X
 Y
```
- `O` = Milk
- `X` = Snowball
- `Y` = Ice Cream Cone


#### Flavored Ice Creams

All flavored ice cream variants can be crafted without Create machinery by combining an ice cream with the matching fluid bucket:

```
 X
 Y
```
- `X` = Flavor bucket
- `Y` = Ice Cream (`create_new_food:icecream`)

| Recipe                | Bucket                  |
|-----------------------|-------------------------|
| Chocolate Ice Cream   | Chocolate Bucket        |
| Pumpkin Ice Cream     | Pumpkin Pulp Bucket     |
| Sweet Berry Ice Cream | Sweet Berry Pulp Bucket |
| Vanilla Ice Cream     | Vanilla Bucket          |
| Melon Ice Cream       | Melon Pulp Bucket       |
| Apple Ice Cream       | Apple Pulp Bucket       |
| Glow Berry Ice Cream  | Glow Berry Pulp Bucket  |


#### Yogurt (1x)

Shapeless — no specific position required:
- 1x Milk (`#create_new_foods:milk`)
- 1x Glass Bottle
- 1x Bone Meal
- 1x Sugar

#### Flavored Yogurts

```
 X
 Y
```
- `X` = Flavor bucket
- `Y` = Yogurt (`create_new_foods:yogurt`)

| Recipe              | Bucket                  |
|---------------------|-------------------------|
| Pumpkin Yogurt      | Pumpkin Pulp Bucket     |
| Sweet Berry Yogurt  | Sweet Berry Pulp Bucket |
| Vanilla Yogurt      | Vanilla Bucket          |
| Melon Yogurt        | Melon Pulp Bucket       |
| Apple Yogurt        | Apple Pulp Bucket       |
| Glow Berry Yogurt   | Glow Berry Pulp Bucket  |

---

#### Cupcake (1x)

Shapeless — no specific position required:
- 1x Wheat-like item (`#create_new_food:wheat_like`)
- 1x Water Bucket
- 1x Milk (`#create_new_food:milk`)
- 1x Egg
- 1x Sugar

#### Flavored Cupcakes

```
 X
 Y
```
- `X` = Frosting bucket
- `Y` = Cupcake (`create_new_food:cupcake_base`)

| Recipe              | Bucket                  |
|---------------------|-------------------------|
| Chocolate Cupcake   | Chocolate Bucket        |
| Pumpkin Cupcake     | Pumpkin Pulp Bucket     |
| Sweet Berry Cupcake | Sweet Berry Pulp Bucket |
| Vanilla Cupcake     | Vanilla Bucket          |
| Melon Cupcake       | Melon Pulp Bucket       |
| Apple Cupcake       | Apple Pulp Bucket       |
| Glow Berry Cupcake  | Glow Berry Pulp Bucket  |

---

### Create Mixing

All mixing recipes require the **Create** mod.

#### Ice Cream Cone (4x)
- 1x wheat-like item
- 1x sugar
- 125 mB milk fluid

#### Cupcake (4x) — requires heat
- 1x dough (`#c:foods/dough`)
- 1x egg
- 1x sugar
- 250 mB milk fluid

#### Pumpkin Pulp
- 1x Pumpkin + 1x Sugar

#### Pumpkin Pulp _(Farmer's Delight - requires both Create and Farmer's Delight)_
- 4x Pumpkin Slice + 1x Sugar

#### Sweet Berry Pulp
- 4x Sweet Berries + 1x Sugar

#### Melon Pulp
- 1x Melon + 1x Sugar

#### Melon Pulp (slices)
- 4x Melon Slice + 1x Sugar

#### Apple Pulp
- 4x Apple + 1x Sugar

#### Glow Berry Pulp
- 4x Glow Berries + 1x Sugar

#### Vanilla
- 2x Vanilla + 1x Sugar

All pulp and vanilla fluid recipes produce **1,000 mB** of fluid. No heat required.

---

### Create Filling

Filling recipes are performed in a Create Spout or filling machine.

| Recipe                | Ingredient     | Fluid (20,250 mB)                                 | Result                |
|-----------------------|----------------|---------------------------------------------------|-----------------------|
| Ice Cream             | Ice Cream Cone | Milk (`#c:milk`)                                  | Ice Cream             |
| Chocolate Ice Cream   | Ice Cream      | Chocolate (`#c:chocolate`)                        | Chocolate Ice Cream   |
| Pumpkin Ice Cream     | Ice Cream      | Pumpkin Pulp (`#create_new_food:pumpkin_pulp`)        | Pumpkin Ice Cream     |
| Sweet Berry Ice Cream | Ice Cream      | Sweet Berry Pulp (`#create_new_food:sweetberry_pulp`) | Sweet Berry Ice Cream |
| Vanilla Ice Cream     | Ice Cream      | Vanilla (`#create_new_food:vanilla`)                  | Vanilla Ice Cream     |
| Melon Ice Cream       | Ice Cream      | Melon Pulp (`#create_new_food:melon_pulp`)            | Melon Ice Cream       |
| Apple Ice Cream       | Ice Cream      | Apple Pulp (`#create_new_food:apple_pulp`)            | Apple Ice Cream       |
| Glow Berry Ice Cream  | Ice Cream      | Glow Berry Pulp (`#create_new_food:glow_berry`)       | Glow Berry Ice Cream  |
| Chocolate Cupcake     | Cupcake        | Chocolate (`#c:chocolate`)                        | Chocolate Cupcake     |
| Pumpkin Cupcake       | Cupcake        | Pumpkin Pulp (`#create_new_food:pumpkin_pulp`)        | Pumpkin Cupcake       |
| Sweet Berry Cupcake   | Cupcake        | Sweet Berry Pulp (`#create_new_food:sweetberry_pulp`) | Sweet Berry Cupcake   |
| Vanilla Cupcake       | Cupcake        | Vanilla (`#create_new_food:vanilla`)                  | Vanilla Cupcake       |
| Melon Cupcake         | Cupcake        | Melon Pulp (`#create_new_food:melon_pulp`)            | Melon Cupcake         |
| Apple Cupcake         | Cupcake        | Apple Pulp (`#create_new_food:apple_pulp`)            | Apple Cupcake         |
| Glow Berry Cupcake    | Cupcake        | Glow Berry Pulp (`#create_new_food:glow_berry`)       | Glow Berry Cupcake    |
| Pumpkin Yogurt        | Yogurt         | Pumpkin Pulp (`#create_new_foods:pumpkin`)            | Pumpkin Yogurt        |
| Sweet Berry Yogurt    | Yogurt         | Sweet Berry Pulp (`#create_new_foods:sweetberry`)     | Sweet Berry Yogurt    |
| Vanilla Yogurt        | Yogurt         | Vanilla (`#create_new_foods:vanilla`)                 | Vanilla Yogurt        |
| Melon Yogurt          | Yogurt         | Melon Pulp (`#create_new_foods:melon`)                | Melon Yogurt          |
| Apple Yogurt          | Yogurt         | Apple Pulp (`#create_new_foods:apple`)                | Apple Yogurt          |
| Glow Berry Yogurt     | Yogurt         | Glow Berry Pulp (`#create_new_foods:glow_berry`)      | Glow Berry Yogurt     |

---

### Farmer's Delight Mixing
_(requires both Create and Farmer's Delight)_

Additional mixing recipes that use Farmer's Delight ingredients.

| Recipe       | Ingredients                 | Result                   |
|--------------|-----------------------------|--------------------------|
| Pumpkin Pulp | 4x Pumpkin Slice + 1x Sugar | Pumpkin Pulp (81,000 mB) |

> Additional Farmer's Delight recipes (tomato sauce, cabbage, pie crust, apple cider) are also included as part of the mod's extended compatibility.
These recipes fix a bug in Farmer's Delight Refabricated v3.4.2
