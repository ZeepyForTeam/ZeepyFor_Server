package com.zeepy.server.user.domain;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.CollectionTable;
import javax.persistence.Column;
import javax.persistence.ElementCollection;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import com.zeepy.server.community.domain.Community;
import com.zeepy.server.community.domain.CommunityLike;
import com.zeepy.server.community.domain.Participation;
import com.zeepy.server.review.domain.Review;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@EqualsAndHashCode
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Setter
@Entity
public class User implements UserDetails {
	@Id
	@EqualsAndHashCode.Include
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "user_sequence_gen")
	@SequenceGenerator(name = "user_sequence_gen", sequenceName = "user_sequence")
	private Long id;

	private String name;

	private String nickname;

	private String email;

	private String password;

	@NotNull
	private Boolean accessNotify;

	private Boolean sendMailCheck;

	@NotNull
	@Enumerated(EnumType.STRING)
	private Role role;

	private String profileImage;

	@ElementCollection
	@CollectionTable(name = "user_address", joinColumns = @JoinColumn(name = "user_id"))
	@Size(max = 3)
	@Column(name = "address")
	private List<Address> addresses = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<CommunityLike> likedCommunities = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Participation> participatingCommunities = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Community> communities = new ArrayList<>();

	@OneToMany(mappedBy = "user")
	private List<Review> reviews = new ArrayList<>();

	@Builder
	public User(Long id, String name, String nickname, String email, String password,
		Boolean accessNotify, Role role, String profileImage, Boolean sendMailCheck) {
		this.id = id;
		this.name = name;
		this.nickname = nickname;
		this.email = email;
		this.password = password;
		this.accessNotify = accessNotify;
		this.sendMailCheck = sendMailCheck;
		this.role = role;
		this.profileImage = profileImage;
	}

	public void setNickNameById() {
		this.nickname = "Zeepy#" + this.id;
	}

	public void setNickname(String name) {
		this.nickname = name;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public void setSendMailCheck() {
		this.sendMailCheck = !this.sendMailCheck;
	}

	public void setProfileImage(String profileImage) {
		this.profileImage = profileImage;
	}

	public Boolean validNickNameLength() {
		return nickname.isEmpty();
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return Collections.singletonList(
			new SimpleGrantedAuthority(this.role.name())
		);
	}

	@Override
	public String getUsername() {
		return email;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	public void setAddress(List<Address> addresses) {
		this.addresses = addresses;
	}

	public Boolean isValidEmail(){
		String regex = "^[_a-z0-9-]+(.[_a-z0-9-]+)*@(?:\\w+\\.)+\\w+$";
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(email);

		return m.matches();
	}

}
