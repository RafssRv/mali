// MISTURADORA = thread que junta 2 vetores JA ordenados em 1 vetor ordenado
// Escreva o codigo embaixo de cada comentario. Travou? Olhe a pasta enxuto.

// [import do Vector]

// [classe Misturadora que E uma Thread]

    // [3 atributos privados: vetor1, vetor2 e resultados (Vector de int[])]

    // [CONSTRUTOR: recebe v1, v2 e rsts]
        // [se algum chegou null -> throw new Exception com mensagem]
        // [guarda os 3 nos atributos usando this]

    // [RUN: o servico da thread]
        // [chama misture com vetor1 e vetor2]
        // [da add do resultado na lista resultados (Vector = thread-safe)]

    // [MISTURE: static, recebe a e b, devolve int[]]
        // [cria c com tamanho a.length + b.length]
        // [i, j e k comecam em 0  (i anda em a, j anda em b, k anda em c)]
        // [while os DOIS tiverem elementos: o menor vai para c[k], anda o dedo dele, k++]
        // [while sobrou em a: copia para c]
        // [while sobrou em b: copia para c]
        // [return c]
