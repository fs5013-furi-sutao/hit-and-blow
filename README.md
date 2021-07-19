## ヒットアンドブロー（クラスを使用しないバージョン）

### 実装する前に

実際にコーディングしていく前に次の作業が必要です。

1. 課題で求められていることを把握する
2. 仕様を固める
3. 必要なメソッドをザッと書いてみる（メソッドの中身は無し）
4. 作りが良いか検証する

ここまでが完了してから、処理の実装をしていきます。

### 1. 課題で求められていることを把握する

課題で求められている要件を確認しておきます。

[55. 作成課題 ⭐ ヒットアンドブロー](https://fs5013-furi-sutao.github.io/java-bootcamp/entry/55-hit-and-blow)

### 2. 仕様を固める

#### 仕様概要

コンピュータが選んだ 4 つの数字の順番と数字を当てるゲームです。

※コンピュータが選ぶ 4 つの数字は、同じ数字は２回以上使わない

コンピュータが選んだ数字を予想して入力します。

順番も数字も合っている場合は「ヒット」
順番は間違っているが入っている数字の場合は「ブロー」
となります。

「ヒット」と「ブロー」の数をヒントに 4 つの数字の順番と数字を当てましょう。

#### 実行例

``` console
[DEBUG] 正解=7854
[1 回目] 4桁の数字を入力してください: 99999
4桁で入力してください 
9999
数字の並びに同じ数字を使わないでください
8912
ヒット：0個、ブロー：1個
[2 回目] 4桁の数字を入力してください: 8934
ヒット：1個、ブロー：1個 
[3 回目] 4桁の数字を入力してください: 8935
ヒット：0個、ブロー：2個 
[ヒント] 1 桁目の数字は 7 です
[4 回目] 4桁の数字を入力してください: 7873
数字の並びに同じ数字を使わないでください
7853
ヒット：3個、ブロー：0個 
[5 回目] 4桁の数字を入力してください: 7852
ヒット：3個、ブロー：0個 
[6 回目] 4桁の数字を入力してください: 7851
ヒット：3個、ブロー：0個 
[ヒント] 2 桁目の数字は 8 です
[7 回目] 4桁の数字を入力してください: 7853
ヒット：3個、ブロー：0個 
[8 回目] 4桁の数字を入力してください: 7854
おめでとう！8回目で成功♪ 
```

#### デバッグモード

デバッグモードを true にしておくと、スタート時に正解が表示される

``` console
[DEBUG] 正解=7854
```

#### ラウンド表示

挑戦回数が「〇回目」と回数表示される

``` console
[3 回目] 4桁の数字を入力してください:
```

#### ヒットとブローの数

ヒットとブローの数が以下の書式で表示される

``` console
ヒット：0個、ブロー：1個
```
#### バリデーション：数字以外の入力

数字以外が入力された場合は、「数字で入力してください」と表示され、再入力が求められ

``` console
[1 回目] 4桁の数字を入力してください: b
数字で入力してください
```

#### バリデーション：指定桁以上の数字の入力

指定桁以外で数字が入力された場合は、「〇桁で入力してください」と表示され、再入力が求められる

``` console 
[1 回目] 4桁の数字を入力してください: 99999
4桁で入力してください 
```

#### バリデーション：重複した数字の使用

指定桁で入力された数字の中に、重複した数字があった場合は、「数字の並びに同じ数字を使わないでください」と表示され、再入力が求められる

``` console 
[1 回目] 4桁の数字を入力してください: 9999
数字の並びに同じ数字を使わないでください
```

#### ヒントの表示

3回ごとに使われている数字を１つずつ教えてくれる

``` console
[3 回目] 4桁の数字を入力してください: 8935
ヒット：0個、ブロー：2個 
[ヒント] 1 桁目の数字は 7 です
[4 回目] 4桁の数字を入力してください:
```