1.
SELECT CODIGO
FROM freguesias
WHERE NOME = "Senhora da Hora";

2.
SELECT PARTIDO, VOTOS
FROM votacoes
WHERE FREGUESIA = "130810";

3.
SELECT COUNT(*)
FROM freguesias
WHERE NOME like "Vilar %";

4.
SELECT NOME
FROM freguesias
WHERE CONCELHO = (    
    SELECT CODIGO
    FROM concelhos
    WHERE NOME = "Espinho");

5.
SELECT CODIGO, NOME
FROM distritos
WHERE CODIGO = (
    SELECT DISTRITO
    FROM concelhos
    WHERE CODIGO = (
        SELECT CONCELHO
        FROM freguesias
        WHERE NOME = "Senhora da Hora"));

6.
SELECT *
FROM listas
WHERE DISTRITO = (
    SELECT CODIGO
    FROM distritos
    WHERE CODIGO = (
        SELECT DISTRITO
        FROM concelhos
        WHERE CODIGO = (
            SELECT CONCELHO
            FROM freguesias
            WHERE NOME = "Senhora da Hora")))
ORDER BY MANDATOS DESC;

7.
SELECT D.NOME AS DISTRITOS, VOTANTES, ABSTENCOES
FROM participacoes P
INNER JOIN distritos D ON P.DISTRITO = D.CODIGO
WHERE (ABSTENCOES * 1.0 / VOTANTES) > 0.75;

8.
SELECT F.Codigo, F.Nome
FROM freguesias F
WHERE F.Concelho IN (
    SELECT C.Codigo
    FROM concelhos C
    WHERE C.DISTRITO = (
        SELECT D.Codigo
        FROM distritos D
        WHERE D.Regiao = 'M'
    )
)
AND F.Codigo IN (
    SELECT V.Freguesia
    FROM votacoes V
    WHERE V.Partido = 'PSN'
    AND V.Votos = 0
);

9.
SELECT P.Sigla, P.Designacao
FROM partidos P
WHERE P.Sigla NOT IN (
    SELECT DISTINCT L.Partido
    FROM listas L
    WHERE L.Distrito = (
        SELECT D.Codigo
        FROM distritos D
        WHERE D.Nome = 'Lisboa'
    )
);

10.
SELECT C.Nome AS Concelhos, P.Sigla AS Partido, SUM(V.Votos) AS Total_Votos
FROM votacoes V
INNER JOIN partidos P ON V.Partido = P.Sigla
INNER JOIN freguesias F ON V.Freguesia = F.Codigo
INNER JOIN concelhos C ON F.Concelho = C.Codigo
GROUP BY C.Nome, P.Sigla
ORDER BY C.Nome, P.Sigla;

11.
SELECT D.Nome AS Distritos, P.Sigla AS Partido, SUM(V.Votos) AS Total_Votos
FROM votacoes V
INNER JOIN partidos P ON V.Partido = P.Sigla
INNER JOIN freguesias F ON V.Freguesia = F.Codigo
INNER JOIN concelhos C ON F.Concelho = C.Codigo
INNER JOIN distritos D on C.DISTRITO = D.Codigo
GROUP BY D.Nome, P.Sigla
ORDER BY D.Nome, P.Sigla;

12.
SELECT P.Sigla AS Partido, SUM(V.Votos) AS Total_Votos
FROM votacoes V
INNER JOIN partidos P ON V.Partido = P.Sigla
GROUP BY P.Sigla
ORDER BY Total_Votos DESC;

13.
SELECT P.Sigla AS Partido, SUM(V.Votos) AS Total_Votos
FROM votacoes V
INNER JOIN partidos P ON V.Partido = P.Sigla
GROUP BY P.Sigla
ORDER BY Total_Votos DESC
LIMIT 5;

14.
SELECT C.Nome AS Concelho, COUNT(F.Codigo) AS Total_Freguesias
FROM concelhos C
INNER JOIN freguesias F ON C.Codigo = F.Concelho
GROUP BY C.Nome
ORDER BY Total_Freguesias DESC
LIMIT 1;

SELECT D.Nome AS Distrito, COUNT(C.Codigo) AS Total_Concelhos
FROM distritos D
INNER JOIN concelhos C ON D.Codigo = C.Distrito
GROUP BY D.Nome
ORDER BY Total_Concelhos DESC
LIMIT 1;

15.
SELECT AVG(Freguesias) AS Media_Freguesias_Por_Concelho
FROM (
    SELECT C.Codigo AS Concelho, COUNT(F.Codigo) AS Freguesias
    FROM concelhos C
    INNER JOIN freguesias F ON C.Codigo = F.Concelho
    GROUP BY C.Codigo
) AS Subconsulta;

16.
SELECT DISTINCT C1.Distrito, C1.Nome AS Concelho1, C2.Nome AS Concelho2
FROM concelhos C1
INNER JOIN concelhos C2 ON C1.Distrito = C2.Distrito
WHERE C1.Codigo < C2.Codigo
AND (
    SELECT COUNT(*) FROM freguesias F1 WHERE F1.Concelho = C1.Codigo
) = (
    SELECT COUNT(*) FROM freguesias F2 WHERE F2.Concelho = C2.Codigo
);

17.
SELECT C.Nome AS Concelho
FROM concelhos C
WHERE C.Distrito = (
    SELECT Distrito
    FROM participacoes
    GROUP BY DISTRITO
    ORDER BY SUM(VOTANTES) DESC
    LIMIT 1
);

18.
SELECT V.Partido AS Partido, F.Nome AS Freguesia, V.Votos
FROM votacoes V
INNER JOIN freguesias F ON V.Freguesia = F.Codigo
ORDER BY V.Votos DESC
LIMIT 1;

19.
SELECT DISTINCT L.Partido AS Partido
FROM listas L
WHERE L.Mandatos = 0
AND L.Partido NOT IN (
    SELECT DISTINCT L2.Partido
    FROM listas L2
    WHERE L2.Mandatos > 0
    AND L2.Distrito IN (
        SELECT D.Codigo
        FROM distritos D
        WHERE UPPER(D.Nome) LIKE '%O%'
    )
);

20.
SELECT D.Nome AS Distrito, P.Designacao AS Partido, MAX(V.Votos) AS Melhor_Votacao
FROM distritos D
INNER JOIN concelhos C ON D.Codigo = C.Distrito
INNER JOIN freguesias F ON C.Codigo = F.Concelho
INNER JOIN votacoes V ON F.Codigo = V.Freguesia
INNER JOIN partidos P ON V.Partido = P.Sigla
WHERE V.Votos = (SELECT MAX(V2.Votos) FROM votacoes V2 WHERE V2.Freguesia = F.Codigo)
GROUP BY D.Nome, P.Designacao;

21.
SELECT F.Concelho AS Concelho, V.Partido AS Partido
FROM freguesias F
INNER JOIN (
    SELECT Concelho, COUNT(DISTINCT Nome) AS Total_Freguesias
    FROM freguesias
    GROUP BY Concelho
) T ON F.Concelho = T.Concelho
INNER JOIN (
    SELECT Freguesia, Partido
    FROM votacoes V
    WHERE V.Votos = (
        SELECT MAX(V2.Votos)
        FROM votacoes V2
        WHERE V2.Freguesia = V.Freguesia
    )
) V ON F.Codigo = V.Freguesia
GROUP BY F.Concelho, V.Partido
HAVING COUNT(DISTINCT F.Codigo) = T.Total_Freguesias
AND F.Concelho IN (
    SELECT Codigo
    FROM concelhos
    WHERE Distrito = (
        SELECT Codigo
        FROM distritos
        WHERE Nome = 'Porto'
    )
);

