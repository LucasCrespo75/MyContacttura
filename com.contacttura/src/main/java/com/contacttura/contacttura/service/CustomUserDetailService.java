package com.contacttura.contacttura.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.contacttura.contacttura.model.User;
import com.contacttura.contacttura.repository.ContactturaRepositoryUser;

@Component
public class CustomUserDetailService implements UserDetailsService{
	
	//Metodo do spring q retura um userDetails, buscando o user atravez do repositorio, recebendo a instancia do repositorio do user local
	private final ContactturaRepositoryUser contactturaRepositoryUser;
	
	@Autowired
	public CustomUserDetailService(ContactturaRepositoryUser contactturaRepositoryUser) {
		this.contactturaRepositoryUser = contactturaRepositoryUser;
	}
	
	//crud padrao de usuario do spring securit 
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = Optional.ofNullable(contactturaRepositoryUser.findByUsername(username))
		.orElseThrow(()-> new UsernameNotFoundException("Usuario nao encontrado"));
		
		//Lista q retorna as autorizações e permissoes para cada tipo de usuairo
		List<GrantedAuthority> authorityListAdmin = AuthorityUtils.createAuthorityList("ROLE_USER", "ROLE_ADIMIN");
		List<GrantedAuthority> authorityListUser = AuthorityUtils.createAuthorityList("ROLE_USER");
		
		
		//Inserindo os dadaos do meu model de usuario diretamento dentro do model de usuario do springSercurity, e validando as permissoes de administrador ou user
		return new org.springframework.security.core.userdetails.User
				(user.getUsername(), user.getPassword(), user.isAdmin() ? authorityListAdmin : authorityListUser);
	}

}
