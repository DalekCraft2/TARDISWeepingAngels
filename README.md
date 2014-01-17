#TARDISWeepingAngels

Inspired by: [TARDIS Ticket #530 - Weeping Angels](http://dev.bukkit.org/bukkit-plugins/tardis/tickets/530-weeping-angels/)

This plugin tranforms skeletons into terrifying Weeping Angels (as seen on  [Doctor Who](http://www.bbc.co.uk/programmes/p00wqr12/profiles/weeping-angels)).

##Features
* Configurable spawn rate
* Configurable drop on death
* Configurable killing item
* Only spawn them in the worlds you want
* Can be frozen in place for a configuarble time

##Information
Weeping Angels only spawn at night in loaded chunks. They spawn with grey leather armour and a water lily helmet (their wings).

Weeping Angels can only be killed with the configured weapon - by default a DIAMOND_PICKAXE - hitting them with anything else has no effect.

The angels move pretty fast, but you can freeze them in place by looking at them and quickly pressing the sneak key. Better arm yourself or flee quickly though, as they'll be after you again in a snap.

##Configuration
The default config is shown below:

```
spawn_rate:
    how_many: 5
    how_often: 400
    max_per_world: 50
worlds:
    - world
freeze_time: 100
weapon: DIAMOND_PICKAXE
drop: STONE
```
The `spawn_rate` section sets Angel spawning options

* `how_many` sets how many Angels to spawn each time.
* `how_often` is the time period (in server ticks - _20 ticks = 1 second_) between spawn attempts.
* `max_per_world` is the maximum number of Weeping Angels can be alive at one time.

The `worlds` section allows you to list the worlds you want the angels to spawn in.

`freeze_time` is the length of time (in server ticks) that the Angels remain motionless for.

`weapon` sets the item that will kill a Weeping Angel.

`drop` sets the item that drops when the Angel is killed
