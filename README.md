# Battleship
ソフトウェア制作用

盤面：
- 10x10

対戦方式：
- 1対n
- 1回の攻撃で他のすべてのプレイヤーの同じ場所を攻撃する。

軍艦の種類：

- 向きを考慮する
- Shipクラスのインスタンスの配列で保存
- 空母　5マス
- 戦艦　4マス
- 巡洋艦　3マス
- 潜水艦　3マス
- 駆逐艦　2マス

1. エントリ順に攻撃
2. 船を配置する
3. 攻撃位置を同時に指定
4. 順番に結果を開示
5. 繰り返し
6. 一人が残るまで繰り返す
