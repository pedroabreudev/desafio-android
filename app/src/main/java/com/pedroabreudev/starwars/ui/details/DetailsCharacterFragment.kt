package com.pedroabreudev.starwars.ui.details

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.pedroabreudev.starwars.data.model.character.CharacterModel
import com.pedroabreudev.starwars.databinding.FragmentDetailsCharacterBinding
import com.pedroabreudev.starwars.ui.base.BaseFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DetailsCharacterFragment :
    BaseFragment<FragmentDetailsCharacterBinding, DetailsCharacterViewModel>() {
    override val viewModel: DetailsCharacterViewModel by viewModels()

    private val args: DetailsCharacterFragmentArgs by navArgs()
    private lateinit var characterModel: CharacterModel

    override fun onCreate(savedInstanceState: Bundle?) {
        setHasOptionsMenu(true)
        super.onCreate(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        characterModel = args.character
        onLoadedCharacter(characterModel)

    }

    @SuppressLint("SetTextI18n")
    private fun onLoadedCharacter(characterModel: CharacterModel) = with(binding) {
        tvNameDetails.text = characterModel.name
        tvHeightDetails.text = characterModel.height + " cm"
        tvGenderDetails.text = characterModel.gender
        tvMassDetails.text = characterModel.mass + " kg"
        tvHairColor.text = characterModel.hair_color
        tvSkinColor.text = characterModel.skin_color
        tvEyeColor.text = characterModel.eye_color
        tvBirthYear.text = characterModel.birth_year

    }

    override fun getViewBinding(
        inflater: LayoutInflater,
        container: ViewGroup?
    ): FragmentDetailsCharacterBinding =
        FragmentDetailsCharacterBinding.inflate(inflater, container, false)
}