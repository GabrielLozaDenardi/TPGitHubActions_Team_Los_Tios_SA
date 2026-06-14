## CI/CD con GitHub Actions

Este proyecto incorpora automatización mediante **GitHub Actions** para compilar, testear y publicar el sistema de transporte trabajado en los TPs anteriores.

La estructura agregada para este TP es:

```text
.github/
  actions/
    build/
      action.yml
    test/
      action.yml
  workflows/
    main.yml
    release.yml
```

## Workflows

### Workflow de CI: `main.yml`

El workflow de **Integración Continua (CI)** se encuentra en:

```text
.github/workflows/main.yml
```

Este workflow se dispara cuando se abre o actualiza un **Pull Request hacia la rama `main`**.

Su objetivo es verificar automáticamente que los cambios propuestos puedan integrarse correctamente al proyecto antes de hacer el merge.

El pipeline tiene dos jobs:

1. `build`
2. `test`

El job `test` depende de `build` mediante:

```yaml
needs: build
```

Esto significa que primero se compila el proyecto. Si la compilación falla, los tests no se ejecutan. Si la compilación pasa, recién ahí se ejecuta la suite de tests.

### Workflow de CD: `release.yml`

El workflow de **Entrega Continua (CD)** se encuentra en:

```text
.github/workflows/release.yml
```

Este workflow se dispara cuando se crea y se sube un tag con formato de versión semántica, por ejemplo:

```text
v1.0.0
v1.2.3
v2.0.0
```

Para dispararlo se puede ejecutar:

```bash
git tag v1.0.0
git push origin v1.0.0
```

Cuando se dispara, el workflow genera el archivo `.jar` del proyecto y publica un **GitHub Release** con el JAR adjunto.

## Actions reutilizables

### Action `build`

La action de build se encuentra en:

```text
.github/actions/build/action.yml
```

Su responsabilidad es preparar el entorno de Java y compilar el proyecto con Gradle.

Ejecuta:

```bash
./gradlew clean compileJava
```

Esta action no genera un release ni sube artefactos. Su único objetivo es verificar que el código compile correctamente.

### Action `test`

La action de test se encuentra en:

```text
.github/actions/test/action.yml
```

Su responsabilidad es preparar el entorno de Java y ejecutar la suite de tests unitarios del proyecto.

Ejecuta:

```bash
./gradlew test
```

Esta action corre los tests del TP anterior, incluyendo los tests de `AlertService`, los tests con fake y los tests con mock usando Mockito.

## Diferencia entre CI y CD en este proyecto

**CI**, o Integración Continua, se usa para verificar automáticamente los cambios antes de integrarlos a la rama principal. En este proyecto, el CI se ejecuta en Pull Requests hacia `main`, compila el código y corre los tests. Su objetivo es detectar errores antes de hacer merge.

**CD**, o Entrega Continua, se usa para preparar y publicar una versión del proyecto. En este proyecto, el CD se ejecuta cuando se crea un tag semántico como `v1.0.0`, genera el JAR y lo publica como un GitHub Release.

En resumen:

```text
CI = verificar cambios antes del merge.
CD = publicar una versión entregable del proyecto.
```

## Evidencia requerida

Para validar el funcionamiento del pipeline se debe contar con:

* Una corrida exitosa del workflow `main.yml` en la pestaña Actions.
* Un Pull Request donde se vean los checks de CI.
* Una corrida fallando a propósito, por ejemplo con un test roto, para demostrar que el pipeline bloquea cambios incorrectos.
* Un Release publicado mediante `release.yml` con el archivo JAR adjunto.
