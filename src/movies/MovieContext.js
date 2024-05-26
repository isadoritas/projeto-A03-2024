import axios from 'axios';
import React, { createContext, useEffect, useState } from 'react';

export const MovieContext = createContext();

export const MovieProvider = props => {
  const [movies, setMovies] = useState([]);

  const loadMovies = async () => {
    const result = await axios.get(`http://localhost:8080/filmes`);
    setMovies(result.data);
  };

  const loadMoviesByName = async (titulo) => {
    try {
      const result = await axios.get(`http://localhost:8080/filmes/${titulo}`);
      setMovies(result.data);
    } catch(error) {
      console.error('Erro ao carregar filmes por nome: ', error);
    }
  };

  useEffect(() => {
    loadMovies();
  }, []);

  return (
    <MovieContext.Provider value={{ movies, loadMoviesByName }}>
      {props.children}
    </MovieContext.Provider>
  );
};
