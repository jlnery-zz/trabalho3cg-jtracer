Ray Tracing no JTrace:
Enunciado Mínimo:
- Interface gráfica para renderização de uma cena utilizando ray trace com JTrace
- O resultado deve ser visualizado na interface
- A cena deve ser renderizada com ambiente, difusa, especular e sombra.
- A cena deve ser montada de modo a poder ser renderizada na hora da apresentação
- A cena deve conter 2 objetos: uma malha de triângulos e um plano abaixo dos dois.
- A malha deve ter uma textura aplicada
- A textura deve ser aplicada utilizando um mapeamento esférico
- Não é necessário deixar o usuário escolher a textura
- A malha deve conter uma bounding box para acelerar a renderização
- A interface deve permitir ao usuário escolher renderizar a cena com ou sem a bounding box.
Extras:
- Outros tipos de mapeamento de textura
- Interpolação bilinear para obter a cor do texel
- Raios secundários:
- Reflexão
- Refração
 - Deixar o usuário escolher a textura e/ou o objeto
...
Super Extras:
- Outras otimizações na malha (Octree, KD-Tree ...)