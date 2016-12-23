# Projeto SADE

Esse README foi escrito para ajudar na configuração do ambiente de desenvolvimento do SADE. Ele está divido em três partes:

  - Quais tecnologias foram utilizadas para o desenvolvimento
  - Como funciona a estrutura de pastas do sistema
  - Como rodar o projeto

### Tecnologias utilizadas

Para o desenvolvimento do projeto, foi utilizado um workspace gerado a partir do [Jhipster](http://jhipster.com). Esse ambiente é um SpringBoot (Java) + AngularJS.

Para o desenvolvimento front-end foram utilizados *webcomponents* do [Angular Material](https://material.angularjs.org/latest/), para a obtenção de um layout mais moderno.

### Estrutura de pastas do sistema

##### Back end

As classes do java estão localizadas para edição no seguinte local:
> <<projeto>>/src/main/java

As estruturas de arquivos estão relativamente padronizadas como os projetos Spring.

##### Front end

Os arquivos front end estão organizados em pastas de acordo com o [John Papa Angular Style Guide](https://github.com/johnpapa/angular-styleguide), bem como a utilização da sintaxe está padronizada pelo mesmo guia (utilização de controllerAs vm e states separados).

A pasta principal de front-end é a:

> <<projeto>>/src/main/webapp

Nelas estão localizados os controllers, services e telas. A estrutura de cada pasta para uma tela é a seguinte:

* Controller (onde são definidos as variáveis de *viewmodel*)
*  State (arquivo que define a rota para essa tela)
*  Html (o html em si da tela)

Tanto o **controller** como o **state** devem ser importados no **index.html** para a criação de uma nova tela.

###### Assets 

A localização dos assets(imagens, css e js) estão localizados na pasta:

> webapp/assets

Como o projeto utiliza **gulp**, há uma task que depois os copia e minifica na pasta **content**

### Rodando o projeto

Após a clonagem do projeto, é necessário ir até:
> src/main/resources/config/application-*.yml

E realizar a configuração do apontamento para o seu banco de dados. Atualmente é necessário utilizar **dois terminais** para manter o front e o back e funcionando : um para rodar o **gulp**  e um para rodar o **java**.

No terminal para subir o servidor é necessário rodar os comandos (estando dentro da pasta principal)
```
$ npm install
$ bower install
$ ./gradlew
```
Já para rodar as tasks do gulp para o funcionamento total do front-end apenas rodar o comando:
```
$ gulp
```

Após isso o servidor estará funcional, por padrão em

## http://localhost:9000
