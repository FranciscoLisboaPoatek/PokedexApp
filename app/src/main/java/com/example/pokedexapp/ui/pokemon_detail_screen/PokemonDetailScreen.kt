package com.example.pokedexapp.ui.pokemon_detail_screen

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import com.example.pokedexapp.R
import com.example.pokedexapp.domain.models.PokemonBaseStats
import com.example.pokedexapp.domain.models.PokemonDetailModel
import com.example.pokedexapp.domain.models.PokemonEvolutionChainModel
import com.example.pokedexapp.domain.models.PokemonSprite
import com.example.pokedexapp.domain.models.PokemonTypes
import com.example.pokedexapp.domain.sample_data.PokemonSampleData
import com.example.pokedexapp.ui.components.NoPokemonImageIcon
import com.example.pokedexapp.ui.components.PokeballLoadingAnimation
import com.example.pokedexapp.ui.components.PokemonDetailTopAppBar
import com.example.pokedexapp.ui.components.PokemonTypeIcon
import com.example.pokedexapp.ui.theme.TopBarBlueColor

@Composable
fun PokemonDetailScreen(
    pokemonDetailViewModel: PokemonDetailViewModel = hiltViewModel(),
    navigateToDetails: (String) -> Unit,
    navigateUp: () -> Unit
) {
    val pokemonDetailState by pokemonDetailViewModel.state.collectAsState()

    fun onEvent(event: PokemonDetailScreenOnEvent) {
        when (event) {
            is PokemonDetailScreenOnEvent.ChangeShinySprite -> {
                pokemonDetailViewModel.changeShinyPokemonSprite(event.sprite)
            }

            is PokemonDetailScreenOnEvent.NavigateUp -> {
                navigateUp()
            }

            is PokemonDetailScreenOnEvent.NavigateToDetails -> {
                navigateToDetails(event.pokemonId)
            }

            is PokemonDetailScreenOnEvent.RotateSprite -> {
                pokemonDetailViewModel.rotatePokemonSprite(event.sprite)
            }
        }
    }

    PokemonDetailScreenContent(
        isLoading = pokemonDetailState.isLoading,
        isError = pokemonDetailState.isError,
        pokemon = pokemonDetailState.pokemonDetailModel,
        currentSprite = pokemonDetailState.pokemonSprite,
        evolutionChain = pokemonDetailState.evolutionChain,
        onEvent = ::onEvent,
        modifier = Modifier.fillMaxSize()

    )
}

@Composable
private fun PokemonDetailScreenContent(
    isLoading: Boolean,
    isError: Boolean,
    pokemon: PokemonDetailModel?,
    currentSprite: PokemonSprite?,
    onEvent: (PokemonDetailScreenOnEvent) -> Unit,
    evolutionChain: PokemonEvolutionChainModel,
    modifier: Modifier = Modifier
) {
    val pokemonImageSize = 200.dp

    PokemonTypesColorBackground(pokemon = pokemon, modifier = modifier) {
        Scaffold(
            containerColor = Color.Transparent,
            topBar = {
                PokemonDetailTopAppBarWrapper(currentSprite = currentSprite, onEvent = onEvent)
            }
        ) {
            Box(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(it)
            )
            {
                PokemonInformationSheetWrapper(
                    isLoading = isLoading,
                    isError = isError,
                    pokemon = pokemon,
                    evolutionChain = evolutionChain,
                    contentTopSpace = pokemonImageSize / 2 - 20.dp,
                    onEvent = onEvent,
                    modifier = Modifier
                        .animateContentSize()
                        .padding(
                            top = pokemonImageSize / 2 + 20.dp,
                            start = 30.dp,
                            end = 30.dp
                        )
                        .padding(bottom = 32.dp)
                        .fillMaxWidth()
                )

                if (!isLoading && !isError) {
                    PokemonImageWrapper(
                        image = currentSprite?.spriteUrl,
                        imageSize = pokemonImageSize,
                        modifier = Modifier
                            .fillMaxWidth()

                    )
                }
            }
        }
    }
}


@Composable
private fun PokemonTypesColorBackground(
    pokemon: PokemonDetailModel?,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    Box(
        modifier = modifier
            .background(
                Brush.linearGradient(
                    colorStops = arrayOf(
                        0f to (pokemon?.primaryType?.color ?: TopBarBlueColor),
                        1f to (pokemon?.secondaryType?.color
                            ?: MaterialTheme.colorScheme.background)
                    )
                )
            )
    ) {
        content()
    }
}

@Composable
private fun PokemonDetailTopAppBarWrapper(
    currentSprite: PokemonSprite?,
    onEvent: (PokemonDetailScreenOnEvent) -> Unit
) {
    if (currentSprite != null) {
        PokemonDetailTopAppBar(
            isShinySprite = currentSprite.spriteType.isShiny,
            rotateSprite = { onEvent(PokemonDetailScreenOnEvent.RotateSprite(currentSprite.spriteType)) },
            changeShinySprite = { onEvent(PokemonDetailScreenOnEvent.ChangeShinySprite(currentSprite.spriteType)) },
            navigateUp = { onEvent(PokemonDetailScreenOnEvent.NavigateUp) })
    } else {
        PokemonDetailTopAppBar(
            isShinySprite = false,
            rotateSprite = { },
            changeShinySprite = { },
            navigateUp = { onEvent(PokemonDetailScreenOnEvent.NavigateUp) })
    }
}

@Composable
private fun PokemonImageWrapper(image: String?, imageSize: Dp, modifier: Modifier = Modifier) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        if (image != null) {
            AsyncImage(
                model = image,
                contentDescription = null,
                modifier = Modifier.size(imageSize)
            )
        } else {
            NoPokemonImageIcon(
                tint = MaterialTheme.colorScheme.onSurface,
                modifier = Modifier.size(imageSize / 2)
            )
        }
    }
}

@Composable
private fun PokemonInformationSheetWrapper(
    isLoading: Boolean,
    isError: Boolean,
    pokemon: PokemonDetailModel?,
    evolutionChain: PokemonEvolutionChainModel,
    contentTopSpace: Dp,
    onEvent: (PokemonDetailScreenOnEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    val informationSheetModifier = Modifier.padding(vertical = contentTopSpace)
    Surface(
        color = MaterialTheme.colorScheme.background,
        shape = RoundedCornerShape(10.dp),
        shadowElevation = 10.dp,
        modifier = modifier
    ) {
        if (isLoading) {
            LoadingPokemonInformationSheet(
                modifier = informationSheetModifier
            )
        } else if (isError) {
            ErrorPokemonInformationSheet(
                modifier = informationSheetModifier
            )
        } else {
            if (pokemon != null) {
                PokemonInformationSheet(
                    pokemon = pokemon,
                    evolutionChain = evolutionChain,
                    onEvent = onEvent,
                    modifier = informationSheetModifier
                )
            }
        }
    }
}

@Composable
private fun LoadingPokemonInformationSheet(modifier: Modifier = Modifier) {
    PokeballLoadingAnimation(modifier = modifier)
}

@Composable
private fun ErrorPokemonInformationSheet(modifier: Modifier = Modifier) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Icon(
            imageVector = Icons.Default.Warning,
            contentDescription = null,
            modifier = Modifier.size(80.dp),
            tint = MaterialTheme.colorScheme.error
        )
        Text(
            text = stringResource(R.string.pokemon_information_sheet_error),
            style = MaterialTheme.typography.titleLarge,
            color = MaterialTheme.colorScheme.error
        )
    }
}

@Composable
private fun PokemonInformationSheet(
    pokemon: PokemonDetailModel,
    evolutionChain: PokemonEvolutionChainModel,
    onEvent: (PokemonDetailScreenOnEvent) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {

        PokemonName(
            pokemonId = pokemon.id,
            pokemonName = pokemon.name,
            modifier = Modifier.padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(24.dp))

        PokemonTypesIcons(
            primaryType = pokemon.primaryType,
            secondaryType = pokemon.secondaryType,
        )

        Spacer(modifier = Modifier.height(32.dp))

        PokemonHeightWeight(
            pokemonHeight = pokemon.height,
            pokemonWeight = pokemon.weight,
            modifier = Modifier
                .padding(horizontal = 8.dp)
        )

        Spacer(modifier = Modifier.height(32.dp))

        PokemonBaseStatsGraph(
            pokemon = pokemon,
            modifier = Modifier
                .padding(horizontal = 12.dp)
                .fillMaxWidth()
        )


        if (evolutionChain.evolvesFromPokemonName != null && evolutionChain.evolvesFromPokemonId != null) {
            Spacer(modifier = Modifier.height(48.dp))

            EvolvesFromPokemon(
                pokemonId = evolutionChain.evolvesFromPokemonId,
                pokemonName = evolutionChain.evolvesFromPokemonName,
                pokemonSpriteUrl = evolutionChain.evolvesFromPokemonSpriteUrl,
                navigateToDetails = { onEvent(PokemonDetailScreenOnEvent.NavigateToDetails(it)) }
            )
        }
    }
}

@Composable
private fun PokemonName(pokemonId: String, pokemonName: String, modifier: Modifier = Modifier) {
    Text(
        buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Gray)) {
                append("#$pokemonId ")
            }
            withStyle(style = SpanStyle()) {
                append(pokemonName)
            }
        },
        overflow = TextOverflow.Ellipsis,
        textAlign = TextAlign.Center,
        style = MaterialTheme.typography.titleLarge.copy(fontSize = 28.sp),
        modifier = modifier,
    )
}

@Composable
private fun PokemonTypesIcons(
    primaryType: PokemonTypes,
    secondaryType: PokemonTypes?,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
        modifier = modifier
    ) {
        PokemonTypeIcon(
            pokemonType = primaryType,
            textPaddingValues = PaddingValues(vertical = 5.dp),
            textStyle = MaterialTheme.typography.titleMedium,
            modifier = Modifier.width(100.dp)
        )
        if (secondaryType != null) {
            PokemonTypeIcon(
                pokemonType = secondaryType,
                textPaddingValues = PaddingValues(vertical = 5.dp),
                textStyle = MaterialTheme.typography.titleMedium,
                modifier = Modifier.width(100.dp)
            )
        }
    }
}

@Composable
private fun PokemonMeasurement(
    content: String,
    icon: Painter,
    color: Color,
    modifier: Modifier = Modifier,
    horizontalArrangement: Arrangement.Horizontal = Arrangement.Start,
) {
    Row(
        horizontalArrangement = horizontalArrangement,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        Icon(
            painter = icon,
            contentDescription = null,
            tint = color,
            modifier = Modifier.size(48.dp)
        )
        Text(
            text = content,
            color = color,
            style = MaterialTheme.typography.titleLarge,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
    }
}


@Composable
private fun PokemonHeightWeight(
    pokemonHeight: Float,
    pokemonWeight: Float,
    modifier: Modifier = Modifier
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(24.dp, Alignment.CenterHorizontally),
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) {
        PokemonMeasurement(
            content = stringResource(id = R.string.weight_in_kg, pokemonWeight.toString()),
            icon = painterResource(id = R.drawable.weight_icon),
            color = Color.Gray,
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End
        )
        PokemonMeasurement(
            content = stringResource(id = R.string.height_in_m, pokemonHeight.toString()),
            icon = painterResource(id = R.drawable.height_icon),
            color = Color.Gray,
            modifier = Modifier.weight(1f)
        )
    }
}

@Composable
private fun BaseStatLabel(pokemonBaseStats: PokemonBaseStats) {
    Text(
        text = stringResource(id = chooseBaseStatStringId(pokemonBaseStats)),
        style = MaterialTheme.typography.titleMedium,
        textAlign = TextAlign.End,
        modifier = Modifier
    )
}

@Composable
private fun BaseStatValue(pokemonBaseStats: PokemonBaseStats) {
    Text(
        text = pokemonBaseStats.value.toString(),
        style = MaterialTheme.typography.titleMedium,
    )
}

private fun chooseBaseStatStringId(pokemonBaseStats: PokemonBaseStats): Int {
    return when (pokemonBaseStats) {
        is PokemonBaseStats.Hp -> R.string.hp_base_stat
        is PokemonBaseStats.Attack -> R.string.attack_base_stat
        is PokemonBaseStats.Defense -> R.string.defense_base_stat
        is PokemonBaseStats.SpecialAttack -> R.string.special_attack_base_stat
        is PokemonBaseStats.SpecialDefense -> R.string.special_defense_base_stat
        is PokemonBaseStats.Speed -> R.string.speed_base_stat
    }
}

@Composable
private fun BaseStatProgressBar(
    statValue: Float,
    color: Color,
    modifier: Modifier = Modifier
) {
    var progress by remember { mutableStateOf(0f) }

    val progressAnimation: Float by animateFloatAsState(
        targetValue = progress,
        animationSpec = tween(1000)
    )

    val maxStat = 200f
    Surface(
        shape = CircleShape,
        modifier = modifier
    ) {
        LinearProgressIndicator(
            progress = { progressAnimation / maxStat } ,
            color = color,
            trackColor = Color.LightGray,
            strokeCap = StrokeCap.Round,
        )
    }

    LaunchedEffect(key1 = Unit) {
        progress = statValue
    }
}

@Composable
private fun PokemonBaseStatsGraph(pokemon: PokemonDetailModel, modifier: Modifier = Modifier) {
    BaseStatsGraph(
        spaceBetweenHorizontally = 8.dp,
        spaceBetweenVertically = 12.dp,
        baseStatsIndex = pokemon.baseStats.size,
        baseStatLabel = {
            BaseStatLabel(
                pokemonBaseStats = pokemon.baseStats[it]
            )
        },
        baseStatBar = {
            BaseStatProgressBar(
                statValue = pokemon.baseStats[it].value.toFloat(),
                color = pokemon.baseStats[it].color,
                modifier = Modifier.height(20.dp)
            )
        },
        baseStatValue = {
            BaseStatValue(pokemonBaseStats = pokemon.baseStats[it])
        },
        modifier = modifier
    )
}

@Composable
private fun EvolvesFromPokemon(
    pokemonId: String,
    pokemonName: String,
    pokemonSpriteUrl: String?,
    navigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.evolves_from),
            style = MaterialTheme.typography.titleLarge
        )
        PokemonImageWrapper(
            image = pokemonSpriteUrl,
            imageSize = 120.dp,
            modifier = Modifier
                .padding(5.dp)
                .clip(RoundedCornerShape(30.dp))
                .clickable { navigateToDetails(pokemonId) }
        )
        Text(text = pokemonName, style = MaterialTheme.typography.titleMedium)
    }
}

@Preview
@Composable
private fun PokemonBaseStatPreview() {
    Surface(color = Color.White) { PokemonBaseStatsGraph(pokemon = PokemonSampleData.singlePokemonDetailSampleData()) }
}

@Preview
@Composable
private fun PokemonHeightWeightPrev() {
    PokemonHeightWeight(
        pokemonWeight = 10f,
        pokemonHeight = 10f
    )
}

@Preview
@Composable
private fun PokemonDetailScreenPreview() {
    val pokemon = PokemonSampleData.singlePokemonDetailSampleData()
    PokemonDetailScreenContent(
        isLoading = false,
        isError = false,
        pokemon = pokemon,
        currentSprite = pokemon.frontDefaultSprite,
        evolutionChain = PokemonEvolutionChainModel(),
        onEvent = { },
    )
}

@Preview
@Composable
private fun PokemonDetailScreenLoadingPreview() {
    PokemonDetailScreenContent(
        isLoading = true,
        isError = false,
        pokemon = null,
        evolutionChain = PokemonEvolutionChainModel(),
        currentSprite = null,
        onEvent = { },
    )
}

@Preview
@Composable
private fun PokemonDetailScreenErrorPreview() {
    PokemonDetailScreenContent(
        isLoading = false,
        isError = true,
        pokemon = null,
        evolutionChain = PokemonEvolutionChainModel(),
        currentSprite = null,
        onEvent = { },
    )
}