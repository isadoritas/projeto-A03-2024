import axios from "axios";
import React, { useEffect, useState } from 'react';


export default function Home() {

  const [movies, setMovies] = useState([])

  useEffect(() => {
    loadMovies();
  }, []);

  const loadMovies = async () => {
    const result = await axios.get(`http://localhost:8080/filmes`);
    setMovies(result.data);
  };

  
  function filtrarGenero(genero) {
    const loadMoviesByGenre = async () => {
      try {
        const result = await axios.get(`http://localhost:8080/filmes/gen/${genero}`);
        setMovies(result.data);
      } catch (error) {
        console.error('Erro ao carregar filmes por gênero: ', error);
      }
    };
    loadMoviesByGenre();
  }

  function loadPesquisa(titulo) {
    const loadMoviesByName = async () => {
      try {
        const result = await axios.get(`http://localhost:8080/filmes/${titulo}`);
        setMovies(result.data);
      } catch(error) {
        console.error('Erro ao carregar filmes por nome: ', error);
      }
    };
    loadMoviesByName();
    
  } 
  
  return (
    <div className='container'>
      <form class="form-inline my-2 my-lg-0 "onSubmit={(e) => e.preventDefault()}>
            <input class="form-control mr-sm-2" type="search" placeholder="Pesquisar" aria-label="Pesquisar" id="searchInput"/>
            <button class="btn btn-outline-secondary my-2 my-sm-0" type="submit" onClick={() => loadPesquisa(document.getElementById('searchInput').value)}>Pesquisar</button>
      </form>
      <div className='py-4'>
        <button className="btn btn-primary mx-2" id="botao_acao" value="28" onClick={() => filtrarGenero(28)}>Ação</button>
        <button className="btn btn-outline-primary mx-2" id="botao_aventura" value="12" onClick={() => filtrarGenero(12)}>Aventura</button>
        <button className="btn btn-danger mx-2" id="botao_animacao" value="16" onClick={() => filtrarGenero(16)}>Animação</button>
        <button className="btn btn-primary mx-2" id="botao_comedia" value="35" onClick={() => filtrarGenero(35)}>Comédia</button>
        <button className="btn btn-outline-primary mx-2" id="botao_crime" value="80" onClick={() => filtrarGenero(80)}>Crime</button>
        <button className="btn btn-danger mx-2" id="botao_documentario" value="99" onClick={() => filtrarGenero(99)}>Documentário</button>
        <button className="btn btn-primary mx-2" id="botao_drama" value="18" onClick={() => filtrarGenero(18)}>Drama</button>
        <button className="btn btn-primary mx-2" id="botao_familia" value="10751" onClick={() => filtrarGenero(10751)}>Familia</button>
        <button className="btn btn-outline-primary mx-2" id="botao_fantasia" value="14" onClick={() => filtrarGenero(14)}>Fantasia</button>
        
        <div className="row">
          {movies.map((movie, index) => (
            <div className="col-md-4 mb-4" key={index}>
              <div className="card">
                <img className="card-img-top" src={movie.poster} alt={movie.titulo} />
                <div className="card-body">
                  <h5 className="card-title">{movie.titulo}</h5>
                  <p className="card-text">{movie.sinopse}</p>
                  <p className="card-text">Avaliação: {movie.avaliacao}</p>
                  <p className="card-text">Data de Lançamento: {movie.dataDeLancamento}</p>
                  <button className="btn btn-primary mx-2">Ver Detalhes</button>
                  <button className="btn btn-outline-primary mx-2">Editar</button>
                  <button className="btn btn-danger mx-2">Excluir</button>
                </div>
              </div>
            </div>
          ))}
        </div>

        {/* <table className="table border shadow">
          <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Titulo</th>
              <th scope="col">Sinopse</th>
              <th scope="col">Avaliação</th>
              <th scope="col">Data de Lançamento</th>
            </tr>
          </thead>
          <tbody>

            {
              movies.map((movie, index) => (
                <tr>
                  <th scope="row" key={index}>{index + 1}</th>
                  <td>{movie.titulo}</td>
                  <td>{movie.sinopse}</td>
                  <td>{movie.avaliacao}</td>
                  <td>{movie.dataDeLancamento}</td>
                  <td>
                    <button className="btn btn-primary mx-2">View</button>
                    <button className="btn btn-outline-primary mx-2">Edit</button>
                    <button className="btn btn-danger mx-2">Delete</button>
                  </td>
                </tr>
              ))
            }
          </tbody>
        </table> */}

      </div>
      
    </div>
    
  )
  
}
