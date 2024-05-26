import axios from 'axios';
import React, { useEffect, useState } from 'react';
import './Navbar.css';


export default function Navbar({ loadPesquisa }) {
  
  const [movies, setMovies] = useState([])

  useEffect(() => {
    loadMovies();
  }, []);

  const loadMovies = async () => {
    const result = await axios.get(`http://localhost:8080/filmes`);
    setMovies(result.data);
  };

  // function loadPesquisa(titulo) {
  //   const loadMoviesByName = async () => {
  //     try {
  //       const result = await axios.get(`http://localhost:8080/filmes/${titulo}`);
  //       setMovies(result.data);
  //     } catch(error) {
  //       console.error('Erro ao carregar filmes por nome: ', error);
  //     }
  //   };
  //   loadMoviesByName();
    
  // } 



  return (
    <div>
      <nav class="navbar navbar-expand-lg navbar-dark bg-dark">
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarTogglerDemo03" aria-controls="navbarTogglerDemo03" aria-expanded="false" aria-label="Alterna navegação">
          <span class="navbar-toggler-icon"></span>
        </button>
        <a class="navbar-brand" href="#">Navbar</a>

        <div class="collapse navbar-collapse" id="navbarTogglerDemo03">
          <ul class="navbar-nav mr-auto">
            <li class="nav-item active">
              <a class="nav-link" href="#">Home</a>
            </li>
            <li class="nav-item">
              <a class="nav-link" href="#">Link</a>
            </li>
          </ul>
          <form class="form-inline my-2 my-lg-0 "onSubmit={(e) => e.preventDefault()}>
            <input class="form-control mr-sm-2" type="search" placeholder="Pesquisar" aria-label="Pesquisar" id="searchInput"/>
            <button class="btn btn-outline-secondary my-2 my-sm-0" type="submit" onClick={() => loadPesquisa(document.getElementById('searchInput').value)}>Pesquisar</button>
          </form>
        </div>
      </nav>
    </div>
  )
}
